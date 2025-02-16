import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.composeExtension
import kaiyrzhan.de.empath.gradle.debugImplementation
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

plugins.applyIfNeeded(libs.plugins.convention.jetbrains.compose.base.get().pluginId)
plugins.apply(libs.plugins.convention.jetpack.compose.base.get().pluginId)

kmpConfig {
    androidConfig {
        buildFeatures {
            compose = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(composeExtension.dependencies.preview)
        }
    }
    dependencies {
        debugImplementation(composeExtension.dependencies.uiTooling)
    }
}
