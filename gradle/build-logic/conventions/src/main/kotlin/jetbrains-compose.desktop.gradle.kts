import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.composeExtension
import kaiyrzhan.de.empath.gradle.kmpConfig

plugins.applyIfNeeded("jetbrains-compose.base")

kmpConfig {
    sourceSets {
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(composeExtension.dependencies.desktop.common)
            implementation(composeExtension.dependencies.desktop.currentOs)
        }
    }
}
