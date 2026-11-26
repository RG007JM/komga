#!/usr/bin/env bash
# Linux/macOS, under the same service account as Komga. No root or GUI session required.
set -euo pipefail
if [[ $# != 1 ]]; then echo "Usage: $0 /path/to/komga/config" >&2; exit 2; fi
root=$(mkdir -p "$1/kcc" && cd "$1/kcc" && pwd)
version=12.1.5
commit=0ee092a3d16a468e276d6d0a389ff0555d466e5e
python=${KCC_PYTHON:-python3.13}
command -v "$python" >/dev/null || { echo 'Python 3.13 with venv is required (or set KCC_PYTHON).' >&2; exit 1; }
archive=7z
[[ $(uname -s) != Darwin ]] || archive=7zz
command -v "$archive" >/dev/null || { echo "Install $archive first." >&2; exit 1; }
command -v git >/dev/null
install="$root/runtime-$version"
if [[ -e "$install" || -e "$root/bin/kcc-c2e" || -e "$root/bin/kcc-c2p" ]]; then
  echo "KCC installation already exists; refusing overwrite: $root" >&2; exit 1
fi
mkdir "$install"
# Venvs contain absolute paths, so install directly at the final path. Remove incomplete install on failure.
cleanup() { rm -rf -- "$install"; }
trap cleanup ERR
# Fetch the tag, then verify its resolved commit against the supplied source checkout.
git clone --depth 1 --branch "v$version" https://github.com/ciromattia/kcc.git "$install/source"
[[ $(git -C "$install/source" rev-parse HEAD) == "$commit" ]]
"$python" -m venv "$install/venv"
# KCC 12.1.5 CLI imports QtCore even though it runs headlessly. Its Docker requirements omit this dependency.
"$install/venv/bin/python" -m pip install --no-input \
  -r "$install/source/requirements-docker.txt" \
  'numpy==2.3.4' 'PyMuPDF==1.26.6' 'PySide6-Essentials' 'requests'
"$install/venv/bin/python" -m pip freeze > "$install/installed-dependencies.txt"
for tool in c2e c2p; do
  "$install/venv/bin/python" "$install/source/kcc-$tool.py" --help > "$install/$tool-help.txt"
  grep -q "v$version " "$install/$tool-help.txt"
done
mkdir -p "$root/bin"
for tool in c2e c2p; do
  # Bash %q safely escapes installation paths containing spaces or shell characters.
  { printf '#!/usr/bin/env bash\nexec %q %q "$@"\n' "$install/venv/bin/python" "$install/source/kcc-$tool.py"; } > "$root/bin/kcc-$tool"
  chmod 755 "$root/bin/kcc-$tool"
done
trap - ERR
printf 'KCC %s installed in %s\n' "$version" "$root"
