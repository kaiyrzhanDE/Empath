import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
plugins.applyIfNeeded(libs.plugins.android.library.get().pluginId)
plugins.applyIfNeeded("kmp.library.base")

kmpConfig {
    androidTarget {
        compilerOptions {
            jvmTarget.set(libs.jvmTarget())
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}

androidConfig {
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
    }
}

plugins.apply("android.base")
