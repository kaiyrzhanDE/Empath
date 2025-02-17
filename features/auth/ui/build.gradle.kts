plugins {
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.all.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.uikit)
            }
        }
    }

    explicitApi()
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.ui"
}
