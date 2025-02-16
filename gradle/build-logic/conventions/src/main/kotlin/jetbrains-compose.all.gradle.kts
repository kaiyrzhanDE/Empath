import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.convention.jetbrains.compose.android.get().pluginId)
plugins.applyIfNeeded(libs.plugins.convention.jetbrains.compose.ios.get().pluginId)
plugins.applyIfNeeded(libs.plugins.convention.jetbrains.compose.desktop.get().pluginId)
