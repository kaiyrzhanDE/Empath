
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.empath.kmp.library.all)
    //Don't use plugin empath.compose.all
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.empath.decompose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)

            api(libs.jetbrains.lifecycle.runtime.compose)
            api(libs.jetbrains.compose.adaptive)
            api(libs.jetbrains.compose.adaptive.layout)
            api(libs.jetbrains.compose.adaptive.navigation)

        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.uikit"

    buildFeatures {
        compose = true
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}