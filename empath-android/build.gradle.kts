plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.empath.android.base)
    alias(libs.plugins.empath.compose.jetpack.base)
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

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = libs.versions.jdkAndroid.get()
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(projects.composeApp)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.koin.android)
}
