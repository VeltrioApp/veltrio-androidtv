plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "app.veltrio.tv"
    compileSdk = 37

    defaultConfig {
        applicationId = "app.veltrio.tv"
        minSdk = 26
        targetSdk = 36
        versionCode = System.getenv("VELTRIO_VERSION_CODE")?.toIntOrNull() ?: 1
        versionName = System.getenv("VELTRIO_VERSION_NAME")?.takeIf { it.isNotBlank() } ?: "1.0.0"
    }

    // Release signing from the environment (the release workflow decodes the
    // keystore secret); without it, release builds come out unsigned.
    val keystore = System.getenv("VELTRIO_KEYSTORE")?.takeIf { file(it).exists() }
    signingConfigs {
        if (keystore != null) create("release") {
            storeFile = file(keystore)
            storePassword = System.getenv("VELTRIO_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("VELTRIO_KEY_ALIAS")
            keyPassword = System.getenv("VELTRIO_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            if (keystore != null) signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":shared"))
    debugImplementation(libs.compose.ui.tooling)
}
