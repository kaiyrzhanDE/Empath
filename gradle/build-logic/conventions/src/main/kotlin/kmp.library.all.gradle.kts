import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.convention.kmp.library.desktop.get().pluginId)
plugins.applyIfNeeded(libs.plugins.convention.kmp.library.android.get().pluginId)
plugins.applyIfNeeded(libs.plugins.convention.kmp.library.ios.get().pluginId)
