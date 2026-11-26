param(
    [Parameter(Mandatory=$true)][string]$ConfigDir,
    [Parameter(Mandatory=$true)][string]$RuntimeDir
)
$ErrorActionPreference = 'Stop'
$arch = 'AMD64'
if ($env:PROCESSOR_ARCHITECTURE -eq 'ARM64' -or $env:PROCESSOR_ARCHITEW6432 -eq 'ARM64') { $arch = 'ARM64' }
# Hashes observed from the supplied AMD64 release downloads; not publisher signatures.
$hashes = @{
    c2e = '228C9614C93E927465ED628B32EDD4BA15F50F8911B9F9702F5F214B95C02ABE'
    c2p = 'FEAE87DD04E86A7C2F67E66491A051713BFF5FAF4B938E9B5FBB7B796B29B53F'
}
$bin = Join-Path $ConfigDir 'kcc\bin'
New-Item -ItemType Directory -Force -Path $bin | Out-Null
foreach ($tool in @('c2e','c2p')) {
    $name = "tool_cli_${tool}_12.1.5_${arch}.exe"
    $source = Join-Path $RuntimeDir $name
    if (!(Test-Path -LiteralPath $source -PathType Leaf)) { throw "Missing $source" }
    if ($arch -eq 'AMD64' -and (Get-FileHash -LiteralPath $source -Algorithm SHA256).Hash -ne $hashes[$tool]) {
        throw "Hash mismatch for $name"
    }
    if ($arch -ne 'AMD64') { throw 'ARM64 checksums are not yet pinned. Use an administrator-verified binary and KOMGA_KCC_C2E instead.' }
    $dest = Join-Path $bin "kcc-$tool.exe"
    if (Test-Path -LiteralPath $dest) {
        if ((Get-FileHash -LiteralPath $dest -Algorithm SHA256).Hash -ne $hashes[$tool]) { throw "Existing $dest differs; refusing overwrite." }
    } else { Copy-Item -LiteralPath $source -Destination $dest }
    $help = & $dest --help 2>&1 | Out-String
    if ($LASTEXITCODE -ne 0 -or $help -notmatch 'v12\.1\.5\s') { throw "$tool probe failed: $help" }
    Write-Host "$tool 12.1.5 verified: $dest"
}
$seven = Get-Command 7z.exe -ErrorAction SilentlyContinue
if (!$seven -and !(Test-Path (Join-Path $env:ProgramFiles '7-Zip\7z.exe'))) {
    Write-Warning '7-Zip is required for CBZ conversion. Install with winget install --id 7zip.7zip --exact, then restart Komga.'
}
