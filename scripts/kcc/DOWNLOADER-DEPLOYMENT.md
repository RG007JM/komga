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
smoke test decodes real synthetic AVIF pixels. KCC's Linux smoke uses mixed
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
