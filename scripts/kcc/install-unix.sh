#!/usr/bin/env bash
# Linux/macOS, under the same service account as Komga. No root or GUI session required.
set -euo pipefail
if [[ $# != 1 ]]; then echo "Usage: $0 /path/to/komga/config" >&2; exit 2; fi
root=$(mkdir -p "$1/kcc" && cd "$1/kcc" && pwd)
version=13.0.4
commit=42119da996979fc3b22ab7b0dc5d73ea2dcb1280
python=${KCC_PYTHON:-python3.13}
command -v "$python" >/dev/null || { echo 'Python 3.13 with venv is required (or set KCC_PYTHON).' >&2; exit 1; }
archive=7z
qt='PySide6-Essentials'
if [[ $(uname -s) == Darwin ]]; then
  archive=7zz
  qt='PySide6-Essentials<=6.10.0'
fi
command -v "$archive" >/dev/null || { echo "Install $archive first." >&2; exit 1; }
command -v git >/dev/null
install="$root/runtime-$version"
if [[ -e "$install" || -L "$install" ]]; then
  echo "KCC installation already exists; refusing overwrite: $install" >&2; exit 1
fi
mkdir "$install"
# Venvs contain absolute paths, so install directly at the final path. Existing versions are not touched.
complete=false
cleanup() { if [[ $complete != true ]]; then rm -rf -- "$install"; fi; }
trap cleanup EXIT
trap 'exit 130' INT
trap 'exit 143' TERM
# Fetch the tag, then verify its resolved commit against the supplied source checkout.
git clone --depth 1 --branch "v$version" https://github.com/ciromattia/kcc.git "$install/source"
[[ $(git -C "$install/source" rev-parse HEAD) == "$commit" ]]
"$python" -m venv "$install/venv"
# Use upstream 13.0.4's headless Docker requirements and its pinned numpy/PyMuPDF versions.
# QtCore is imported by the CLI, even without the GUI. Requests follows upstream requirements.txt.
"$install/venv/bin/python" -m pip install --no-input \
  -r "$install/source/requirements-docker.txt" \
  'numpy==2.3.4' 'PyMuPDF==1.26.6' "$qt" 'requests>=2.34.2'
"$install/venv/bin/python" -m pip freeze > "$install/installed-dependencies.txt"
"$install/venv/bin/python" "$install/source/kcc-c2e.py" --help > "$install/c2e-help.txt"
grep -Fq "comic2ebook v$version " "$install/c2e-help.txt"
# Publish only the conversion CLI after its probe succeeds.
mkdir "$install/bin-staging"
{ printf '#!/usr/bin/env bash\nexec %q %q "$@"\n' "$install/venv/bin/python" "$install/source/kcc-c2e.py"; } > "$install/bin-staging/kcc-c2e"
chmod 755 "$install/bin-staging/kcc-c2e"
mv "$install/bin-staging" "$install/bin"
complete=true
printf 'KCC %s installed in %s\n' "$version" "$install/bin"
