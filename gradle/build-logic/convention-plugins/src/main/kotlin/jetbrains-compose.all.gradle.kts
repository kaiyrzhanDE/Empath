import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.empath.compose.android.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.compose.ios.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.compose.desktop.get().pluginId)
