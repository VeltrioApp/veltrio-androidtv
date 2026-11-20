#!/usr/bin/env bash
#
# The core comes with the shared module: the submodule's script puts it in
# android/core/, at the version the submodule pins (android/core.version).
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
[ -f "$ROOT/android/scripts/fetch-core.sh" ] || git -C "$ROOT" submodule update --init
exec "$ROOT/android/scripts/fetch-core.sh"
