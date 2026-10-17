plugins {
    id("com.android.application")
}

android {
    namespace = "com.folio.notesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.folio.notesapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

kotlin {
    jvmToolchain(17)
}