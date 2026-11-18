# Veltrio for Android TV

Not started yet. This repository holds the CI/CD and the pinned
[veltrio-core](https://github.com/VeltrioApp/veltrio-core) release the
app will build on.

**Planned stack:** Kotlin + Compose for TV (leanback launcher, D-pad focus), minSdk 26.

## The core

`core.version` names the veltrio-core release to use;
`scripts/fetch-core.sh` downloads its `veltrio-core-android.zip` into `core/`
(gitignored). Needs [`gh`](https://cli.github.com) signed in with access
to `VeltrioApp/veltrio-core`, or `VELTRIO_CORE_DIR=../veltrio-core` to
build from a local checkout.

Same core artifact as veltrio-android. If the two apps end up sharing most UI code, consider one repo with phone and TV modules instead.

## CI/CD

- `ci.yml` — pushes to main, pull requests, manual runs: fetches the core, then builds and tests once
  `settings.gradle.kts` exists. Until then it only checks the core download.
- `release.yml` — tag `vX.Y.Z`: signed `.aab` + APK attached to the GitHub release (Play Console upload to add). Skipped until the project exists.

Organisation secrets used: `CORE_RELEASES_TOKEN`, `ANDROID_KEYSTORE`, `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD`.
