#!/usr/bin/env python3
"""Real KCC smoke test using only generated test images."""
import argparse
import pathlib
import subprocess
import tempfile
import zipfile

from PIL import Image, ImageDraw
import pymupdf

parser = argparse.ArgumentParser()
parser.add_argument('c2e', type=pathlib.Path)
args = parser.parse_args()
with tempfile.TemporaryDirectory(prefix='komga-kcc-smoke-') as tmp:
    root = pathlib.Path(tmp)
    source = root / 'fixture.cbz'
    with zipfile.ZipFile(source, 'w') as archive:
        for n in range(1, 4):
            img = Image.new('RGB', (800, 1200), 'white')
            draw = ImageDraw.Draw(img)
            draw.rectangle((50, 50, 750, 1150), outline='black', width=8)
            draw.rectangle((150, 150, 650, 400), fill=(40, 100, 160))
            draw.ellipse((200, 500, 600, 900), fill=(160, 60, 40))
            draw.text((100, 100), f'Fixture page {n}', fill='black')
            page = root / (f'{n:04}.avif' if n == 1 else f'{n:04}.png')
            img.save(page)
            archive.write(page, page.name)
        archive.writestr('ComicInfo.xml', '<ComicInfo><Title>Fixture</Title><Series>Test</Series><Number>1</Number></ComicInfo>')
    for fmt in ('CBZ', 'EPUB', 'PDF'):
        output = root / fmt
        output.mkdir()
        subprocess.run([
            str(args.c2e.resolve()), '--profile=KoLC', f'--format={fmt}', '--manga-style',
            '--forcecolor', '--cropping=0', '--keepcomicinfo=1', f'--output={output}', '--', str(source)
        ], check=True, timeout=300)
        files = list(output.iterdir())
        assert len(files) == 1, files
        result = files[0]
        if fmt == 'PDF':
            with pymupdf.open(result) as document:
                assert document.page_count == 3
        else:
            with zipfile.ZipFile(result) as archive:
                assert archive.testzip() is None
                if fmt == 'EPUB':
                    assert archive.read('mimetype') == b'application/epub+zip'
                    assert 'META-INF/container.xml' in archive.namelist()
                else:
                    assert len([n for n in archive.namelist() if n.lower().endswith(('.jpg', '.jpeg', '.png'))]) == 3
                    assert any(n.lower().endswith('comicinfo.xml') for n in archive.namelist())
        assert source.is_file(), 'Original disappeared'
        print(f'{fmt} OK: {result.name} ({result.stat().st_size} bytes)', flush=True)
