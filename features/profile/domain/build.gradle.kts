plugins {
    alias(libs.plugins.empath.kmp.library.all)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.utils)
                implementation(projects.core.network)
            }
        }
    }
}


android {
    namespace = "kaiyrzhan.de.empath.features.profile.domain"
}
