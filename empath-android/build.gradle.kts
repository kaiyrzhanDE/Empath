
plugins {
    alias(libs.plugins.android.application)
    id(libs.plugins.convention.android.base.get().pluginId)
    id(libs.plugins.convention.jetpack.compose.base.get().pluginId)
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

    kotlinOptions {
        jvmTarget = libs.versions.jdk.get()
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(projects.composeApp)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
}
