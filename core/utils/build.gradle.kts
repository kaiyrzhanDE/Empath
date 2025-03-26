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

            api(libs.filekit.core)
            api(libs.filekit.coil)
            api(libs.filekit.dialogs)
            api(libs.filekit.dialogs.compose)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.utils"
}
