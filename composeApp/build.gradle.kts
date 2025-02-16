plugins {
    alias(libs.plugins.convention.kmp.library.all)
    alias(libs.plugins.convention.jetbrains.compose.all)
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
