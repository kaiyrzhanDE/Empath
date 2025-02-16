plugins {
    alias(libs.plugins.android.library)
    id("kmp.library.all")
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.uikit"

    buildFeatures {
        compose = true
    }
}
