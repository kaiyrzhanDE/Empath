plugins {
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.compose.all)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.features.auth.domain)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.ui"
}
