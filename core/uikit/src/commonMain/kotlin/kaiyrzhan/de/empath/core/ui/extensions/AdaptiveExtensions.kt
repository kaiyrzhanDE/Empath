package kaiyrzhan.de.empath.core.ui.extensions

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.window.core.layout.WindowWidthSizeClass


public fun WindowAdaptiveInfo.isPhone(): Boolean {
    return this.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
}