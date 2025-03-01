plugins {
    alias(libs.plugins.empath.kmp.library.all)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.network)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.data"
}
