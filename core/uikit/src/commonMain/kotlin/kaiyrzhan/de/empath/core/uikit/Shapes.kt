package kaiyrzhan.de.empath.core.uikit

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes as M3Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp


internal val LocalShapes = staticCompositionLocalOf { shapes() }

internal fun shapes(): Shapes {
    return Shapes(
        none = RoundedCornerShape(size = 0.dp),
        extraSmall = RoundedCornerShape(size = 4.dp),
        small = RoundedCornerShape(size = 8.dp),
        medium = RoundedCornerShape(size = 12.dp),
        large = RoundedCornerShape(size = 16.dp),
        extraLarge = RoundedCornerShape(size = 28.dp),
        full = RoundedCornerShape(percent = 50),
    )
}

@Immutable
public data class Shapes(
    val none: CornerBasedShape,
    val extraSmall: CornerBasedShape,
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape,
    val extraLarge: CornerBasedShape,
    val full: CornerBasedShape,
)

internal fun Shapes.toM3Shapes(): M3Shapes {
    return M3Shapes(
        small = small,
        medium = medium,
        large = large,
        extraLarge = extraLarge,
        extraSmall = extraSmall,
    )
}
