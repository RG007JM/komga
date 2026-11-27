param(
    [Parameter(Mandatory=$true)][string]$ConfigDir,
    [Parameter(Mandatory=$true)][string]$RuntimeDir
)
$ErrorActionPreference = 'Stop'
$version = '13.0.4'
$arch = if ($env:PROCESSOR_ARCHITEW6432) { $env:PROCESSOR_ARCHITEW6432 } else { $env:PROCESSOR_ARCHITECTURE }
# SHA-256 digests published in the upstream v13.0.4 GitHub release metadata.
$hashes = @{
    AMD64 = 'AC245FE431063875CBE5290731094356655ACCCE3217048AE3ECB7E63F210C22'
    ARM64 = 'DB4F705BDF2DA4F7BE206C2EEA262BD3D80E1E11DD364F016873CBD6CA40DD6C'
}
if (-not $hashes.ContainsKey($arch)) { throw "Unsupported Windows architecture: $arch" }
# Verify the only required CLI before copying anything. Leave earlier runtimes intact.
$name = "tool_cli_c2e_${version}_${arch}.exe"
$source = Join-Path $RuntimeDir $name
if (!(Test-Path -LiteralPath $source -PathType Leaf)) { throw "Missing $source" }
if ((Get-FileHash -LiteralPath $source -Algorithm SHA256).Hash -ne $hashes[$arch]) {
    throw "Hash mismatch for $name"
}
$root = Join-Path $ConfigDir 'kcc'
New-Item -ItemType Directory -Force -Path $root | Out-Null
$install = Join-Path $root "runtime-$version"
if (Test-Path -LiteralPath $install) { throw "KCC $version already exists; refusing overwrite: $install" }
$stage = Join-Path $root ('.install-' + [Guid]::NewGuid().ToString('N'))
try {
    $bin = Join-Path $stage 'bin'
    New-Item -ItemType Directory -Path $bin -Force | Out-Null
    $dest = Join-Path $bin 'kcc-c2e.exe'
    Copy-Item -LiteralPath $source -Destination $dest
    if ((Get-FileHash -LiteralPath $dest -Algorithm SHA256).Hash -ne $hashes[$arch]) { throw 'Copied asset hash mismatch' }
    $help = & $dest --help 2>&1 | Out-String
    if ($LASTEXITCODE -ne 0 -or $help -notmatch ('(?m)^comic2ebook v' + [regex]::Escape($version) + '\s')) { throw "KCC probe failed: $help" }
    # Directory.Move refuses an existing destination; publish the verified CLI.
    [System.IO.Directory]::Move($stage, $install)
} finally {
    if (Test-Path -LiteralPath $stage) { Remove-Item -LiteralPath $stage -Recurse -Force }
}
Write-Host "KCC $version verified: $(Join-Path $install 'bin')"
$seven = Get-Command 7z.exe -ErrorAction SilentlyContinue
if (!$seven -and !(Test-Path (Join-Path $env:ProgramFiles '7-Zip\7z.exe'))) {
    Write-Warning '7-Zip is required for CBZ conversion. Install it separately, then restart Komga.'
}
