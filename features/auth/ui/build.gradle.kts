plugins {
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.compose.all)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.utils)
            }
        }
    }
}

compose.resources{
    publicResClass = false
    generateResClass = always
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.ui"
}
