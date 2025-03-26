import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.jetbrains
import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.kotlin
import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.multiplatform
import kaiyrzhan.de.empath.gradle.ProjectTargets
import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.plugins

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.kmp.library.base.get().pluginId)
plugins.applyIfNeeded(
    libs.plugins.android.library.get().pluginId,
    libs.plugins.android.application.get().pluginId,
)

kmpConfig {

    androidTarget {
        compilerOptions {
            jvmTarget.set(libs.jvmTarget(ProjectTargets.Android))
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

plugins.apply(libs.plugins.empath.android.base.get().pluginId)

