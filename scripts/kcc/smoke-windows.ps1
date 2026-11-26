param(
    [Parameter(Mandatory=$true)][string]$ConfigDir,
    [Parameter(Mandatory=$true)][string]$InputFile
)
$ErrorActionPreference = 'Stop'
$exe = Join-Path $ConfigDir 'kcc\bin\kcc-c2e.exe'
if (!(Test-Path -LiteralPath $exe -PathType Leaf)) { throw 'Run install-windows.ps1 first.' }
$seven = Get-Command 7z.exe -ErrorAction SilentlyContinue
if (!$seven -and !(Test-Path (Join-Path $env:ProgramFiles '7-Zip\7z.exe'))) { throw 'Install 7-Zip first: winget install --id 7zip.7zip --exact' }
$root = Join-Path $env:TEMP ('komga-kcc-smoke-' + [Guid]::NewGuid().ToString('N'))
$original = Get-Item -LiteralPath $InputFile
if ($original.Extension -ne '.cbz') { throw 'Expected a CBZ input.' }
$hash = (Get-FileHash -LiteralPath $original.FullName).Hash
New-Item -ItemType Directory -Path $root | Out-Null
$input = Join-Path $root 'input.cbz'
Copy-Item -LiteralPath $original.FullName -Destination $input
Add-Type -AssemblyName System.IO.Compression.FileSystem
foreach ($format in @('CBZ','EPUB','PDF')) {
    $out = Join-Path $root $format
    New-Item -ItemType Directory -Path $out | Out-Null
    $cliArgs = @('--profile=KoLC',"--format=$format",'--manga-style','--forcecolor','--cropping=0','--keepcomicinfo=1',"--output=$out",'--',$input)
    Write-Host "`n=== $format ==="
    & $exe @cliArgs
    if ($LASTEXITCODE -ne 0) { throw "KCC $format conversion failed; files retained in $root" }
    $outputs = @(Get-ChildItem -LiteralPath $out -File)
    if ($outputs.Count -eq 0) { throw "KCC produced no $format output" }
    foreach ($file in $outputs) {
        if ($file.Extension -ne ".$($format.ToLowerInvariant())" -or $file.Length -eq 0) { throw "Invalid output: $($file.FullName)" }
        if ($format -ne 'PDF') {
            $zip = [IO.Compression.ZipFile]::OpenRead($file.FullName)
            try {
                if ($format -eq 'EPUB' -and (!$zip.GetEntry('mimetype') -or !$zip.GetEntry('META-INF/container.xml'))) { throw 'Invalid EPUB structure' }
                if ($format -eq 'CBZ' -and !($zip.Entries | Where-Object { $_.Name -match '\.(jpg|jpeg|png|webp)$' })) { throw 'CBZ contains no pages' }
            } finally { $zip.Dispose() }
        }
        $file | Select-Object Name,Length
    }
}
if (!(Test-Path -LiteralPath $input) -or (Get-FileHash -LiteralPath $original.FullName).Hash -ne $hash) { throw 'Original-file check failed.' }
Write-Host "`nKCC smoke outputs retained at $root. Original unchanged."
