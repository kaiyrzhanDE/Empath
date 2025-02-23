import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.empath.kmp.library.desktop.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.kmp.library.android.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.kmp.library.ios.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.koin.get().pluginId)
