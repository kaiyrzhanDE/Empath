import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.composeExtension
import kaiyrzhan.de.empath.gradle.debugImplementation
import kaiyrzhan.de.empath.gradle.kmpConfig
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

plugins.applyIfNeeded("jetbrains-compose.base")
plugins.apply("jetpack-compose.base")

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
