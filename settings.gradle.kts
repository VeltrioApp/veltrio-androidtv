pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    // Same versions as the phone app, from the submodule.
    versionCatalogs {
        create("libs") { from(files("android/gradle/libs.versions.toml")) }
    }
}

rootProject.name = "VeltrioTV"

// shared: the phone app's shared module (veltrio-android, a git submodule in
// android/): the core, the services, the player and the screens.
// tv: the Android TV app.
include(":shared", ":tv")
project(":shared").projectDir = file("android/shared")
