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

            api(libs.filekit.core)
            api(libs.filekit.coil)
            api(libs.filekit.dialogs)
            api(libs.filekit.dialogs.compose)

            api(libs.coil.network.ktor)
            api(libs.coil.compose)

            implementation(projects.core.utils)
            implementation(projects.core.network)
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