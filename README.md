# Veltrio for Android TV

The Veltrio app for Android TV and Google TV: the same features and screens
as the phone app (and the macOS one it follows), driven with a remote.

The phone app's repository, [veltrio-android](https://github.com/VeltrioApp/veltrio-android),
is a git submodule in `android/`. Its `shared` module holds the core, the
services, the libmpv player and the screens; this repository adds the `tv`
module: the TV activity (the sections in a rail on the left), the leanback
launcher entry and banner. In the shared screens, the TV app gets an amber
focus frame on whatever the remote is on, and the player's remote keys:

- OK, or up: show the controls; down on a live channel: the channel list.
- Left / right while the controls are hidden: 10 s back / forward (on a
  live channel: the previous / next channel). Channel up / down zaps too.
- Play / pause, rewind / fast forward (30 s).
- Back hides the controls first, then leaves the player.

## Build

```bash
git clone --recurse-submodules https://github.com/VeltrioApp/veltrio-androidtv
./scripts/fetch-core.sh          # the core, at the version android/core.version pins
./gradlew :tv:assembleDebug      # tv/build/outputs/apk/debug/tv-debug.apk
```

JDK 21 and the Android SDK, as for the phone app. Updating the shared code:
`git -C android pull origin main`, then commit the new submodule commit here.

## CI/CD

- `ci.yml` — pushes to main, pull requests, manual runs: checks out the
  submodule, fetches the core, builds the TV app.
- `release.yml` — tag `vX.Y.Z`: signed `.aab` + APK attached to the GitHub
  release.

Organisation secrets used: `CORE_RELEASES_TOKEN` (optional), `ANDROID_KEYSTORE`,
`ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD`;
`VELTRIO_TRAKT_CLIENT_ID`, `VELTRIO_TRAKT_CLIENT_SECRET`, `VELTRIO_SIMKL_CLIENT_ID`
(optional, for the Trakt and SIMKL accounts).

## License

Dual-licensed under either of [Apache-2.0](LICENSE-APACHE) or [MIT](LICENSE-MIT). libmpv (`dev.jdtech.mpv:libmpv`) is LGPL.
