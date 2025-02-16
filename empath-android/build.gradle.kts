plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.convention.android.base)
    alias(libs.plugins.convention.jetpack.compose.base)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kaiyrzhan.de.empath"

    defaultConfig {
        applicationId = "kaiyrzhan.de.empath"
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lint {
        checkReleaseBuilds = false
        checkDependencies = true
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(projects.composeApp)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.core)
}
