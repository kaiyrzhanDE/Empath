plugins {
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.all.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.composeapp"
}
