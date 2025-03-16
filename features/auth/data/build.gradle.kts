plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.ktor)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.network)
                implementation(projects.features.auth.domain)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.data"
}
