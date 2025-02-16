plugins {
    id("kmp.library.all")
    id("jetbrains-compose.all")
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
