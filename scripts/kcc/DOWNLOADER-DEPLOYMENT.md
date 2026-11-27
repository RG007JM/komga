# Downloader deployment and migration

Use existing Komga configuration and systemd, not a custom `.env` loader.
For systemd standalone JAR deployments, add a drop-in with `[Service]` and
`EnvironmentFile=/opt/komga/mangaplus.env`. Create that file with restrictive
permissions (0600, owned by the service administrator), and provide the existing
identity through `MANGAPLUS_DEVICE_SECRET`, `MANGAPLUS_OS_VER`,
`MANGAPLUS_APP_VER`, and `MANGAPLUS_COUNTRY_CODE`. Enable the provider with
`KOMGA_DOWNLOADS_MANGAPLUS_ENABLED=true`. Do not commit the environment file or
identity. The JVM launch order is `java --enable-native-access=ALL-UNNAMED -Xmx2g -jar /opt/komga/komga.jar`.
Environment variables remain visible to privileged processes; file
permissions alone do not constitute a secret vault.

Use the existing NightMonkeys ImageIO plugins and system libheif with an AV1
codec for AVIF. The standalone JAR may need `--enable-native-access=ALL-UNNAMED`
and an appropriate library path. On Windows use the bundled tray DLL directory
on PATH and java.library.path. Verify AVIF support in Healthcheck and the
startup image format list. No FFmpeg or ImageMagick dependency is added.
The runtime healthcheck reports registered AVIF readers; the opt-in Linux CI
smoke test decodes real synthetic AVIF pixels. KCC's opt-in Kotlin/JUnit smoke test uses mixed
AVIF/PNG input. The metadata-only provider probe is explicitly requested and
checks one representative tracked series per provider, never chapter pages.

Existing auto-fetch records with no catalogue baseline are safely reseeded on
first successful check. This intentionally does not download any unselected
existing chapter (including releases that appeared before migration). Explicit
pending selections are retained. Changing provider catalogue version, language,
groups or destination establishes a new baseline. Numbered releases must
advance the observed chapter-number watermark (even if a source republishes an
older chapter with a new date). Undated releases without comparable numeric
numbers, older-numbered specials and alternate editions require explicit
selection. Eligible new chapters are persisted before the watermark advances,
so failed or unprocessed chapters remain eligible across restarts. Pausing/deleting stops the next chapter, not an
already-running conversion. Automatic batches are limited to ten chapters per
check. Manual downloads remain explicitly selected.

Interrupted conversions are marked failed on startup, preserving their recorded
source for explicit retry. If interruption occurred during publication, inspect
any existing output before retry: output collisions are never overwritten.
Temporary files can disappear across system reboots; if the recorded source is
missing, manually select the chapter again. Completed outputs awaiting a scan
are requeued. Import status is checked against Komga's book and media records;
scan enqueue alone is not proof of import. No book, history or library is
automatically deleted by migration or recovery.

## Real KCC smoke tests (Kotlin/JUnit)

The same `KccSmokeTest` exercises the production conversion pipeline with
synthetic mixed AVIF/PNG input, a Unicode chapter filename, and real CBZ,
EPUB (KEPUB profile), and PDF output. It verifies source preservation, output
validation, page counts where applicable, and restored chapter metadata.
No external manga provider or system Python is used by the test code.
Ordinary Gradle tests skip this opt-in class unless `KOMGA_TEST_KCC=true`.

Windows (PowerShell, after installing the official CLI and 7-Zip):

```powershell
$env:KOMGA_TEST_KCC = 'true'
$env:KOMGA_KCC_C2E = 'C:\path\to\komga-config\kcc\runtime-13.0.4\bin\kcc-c2e.exe'
try {
    .\gradlew.bat :komga:test --rerun --tests '*KccSmokeTest' --console=plain
    if ($LASTEXITCODE -ne 0) { throw 'KCC smoke failed' }
} finally {
    Remove-Item Env:KOMGA_TEST_KCC -ErrorAction SilentlyContinue
    Remove-Item Env:KOMGA_KCC_C2E -ErrorAction SilentlyContinue
}
```

Linux/macOS (after installing the external CLI and archive utility):

```sh
KOMGA_TEST_KCC=true KOMGA_KCC_C2E=/path/to/komga-config/kcc/runtime-13.0.4/bin/kcc-c2e \
  ./gradlew :komga:test --rerun --tests '*KccSmokeTest' --console=plain
```

KCC itself is an external Python application. Official Windows CLI executables
bundle their interpreter. The pinned Unix installer and optional Docker
wrapper still use an isolated Python environment for upstream KCC 13.0.4;
this is not Komga test or production implementation code. A compatible,
administrator-verified standalone CLI can be selected with `KOMGA_KCC_C2E`
without using the Unix installer. Do not substitute the GUI AppImage or DMG
for a tested headless CLI. No automatic runtime installation is done by Komga.

## Upgrading the external KCC runtime to 13.0.4

The supported CLI is pinned to 13.0.4 (upstream tag commit
`42119da996979fc3b22ab7b0dc5d73ea2dcb1280`). All existing supported device
codes and CLI options are unchanged. The new `--filename` option remains
managed by Komga (not user-selectable), like `--output` and `--delete`.
Existing KCC profiles and download history need no migration.

Installers create `<config>/kcc/runtime-13.0.4/bin/` and never overwrite
`<config>/kcc/bin/` or another version's files. Komga checks an explicit
`KOMGA_KCC_C2E` override first, then the pinned version directory, then
legacy locations and PATH. If an explicit path points to an old version,
update or remove the override before using the new JAR. A missing or wrong
version disables KCC conversion, not Komga or ordinary CBZ downloads.

Windows: download only the official AMD64 or ARM64 `tool_cli_c2e`
release asset for 13.0.4 and pass its directory to
`install-windows.ps1 -ConfigDir <config> -RuntimeDir <downloads>`. The
installer verifies its published SHA-256 digest. The separate panel CLI
is not needed: KCC's conversion engine already includes panel processing
for webtoon mode. Keep upstream source modules intact.
Linux/macOS: run `bash scripts/kcc/install-unix.sh <config>` as the service
administrator, with Python and the archive utility already available.
The Unix installer installs upstream dependencies only inside its venv;
its dependencies are not fully locked to exact hashes. Keep the old JAR
and runtime until the Kotlin smoke tests and a real conversion succeed.
