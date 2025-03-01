plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.empath.kmp.library.all)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.utils"
}
