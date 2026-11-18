#!/usr/bin/env bash
#
# Put the Rust core where the project expects it (core/, gitignored):
#
#   core/jniLibs/<abi>/libveltrio_core.so
#   core/kotlin/uniffi/veltrio/veltrio.kt
#
# Downloads `veltrio-core-android.zip` from the veltrio-core release named in
# core.version (needs `gh` signed in with access to VeltrioApp/veltrio-core,
# or GH_TOKEN in CI). VELTRIO_CORE_DIR=../veltrio-core builds it from a
# local checkout instead.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="$ROOT/core"
VERSION="$(tr -d '[:space:]' < "$ROOT/core.version")"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

if [ -n "${VELTRIO_CORE_DIR:-}" ]; then
  CORE="$(cd "$VELTRIO_CORE_DIR" && pwd)"
  echo "==> Building the core from $CORE"
  "$CORE/scripts/package-android.sh"
  cp "$CORE/dist/veltrio-core-android.zip" "$TMP/veltrio-core-android.zip"
else
  echo "==> veltrio-core $VERSION"
  gh release download "$VERSION" --repo VeltrioApp/veltrio-core --pattern veltrio-core-android.zip --dir "$TMP"
fi

rm -rf "$OUT" && mkdir -p "$OUT"
unzip -q "$TMP/veltrio-core-android.zip" -d "$OUT"
echo "==> core/ ready"
