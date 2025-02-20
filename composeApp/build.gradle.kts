plugins {
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.all.get().pluginId)
    id(libs.plugins.convention.koin.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.auth.ui)
            implementation(projects.core.utils)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.composeapp"
}
