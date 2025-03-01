package kaiyrzhan.de.empath.core.uikit

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
internal fun rippleConfiguration(): RippleConfiguration {
    return RippleConfiguration(
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0.08f,
            draggedAlpha = 0.12f,
            focusedAlpha = 0.16f,
            hoveredAlpha = 0.16f,
        )
    )
}
