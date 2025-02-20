@file:Suppress("unused")
package kaiyrzhan.de.empath.core.uikit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
public fun EmpathTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: ColorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme(),
    typography: Typography = typography(),
    shapes: Shapes = shapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorScheme provides colors,
        LocalShapes provides shapes,
    ) {
        MaterialTheme(
            colorScheme = colors.toM3ColorScheme(),
            typography = typography,
            shapes = shapes.toM3Shapes(),
            content = content,
        )
    }
}

public object EmpathTheme {
    public val colors: ColorScheme
        @[Composable ReadOnlyComposable] get() = LocalColorScheme.current

    public val typography: Typography
        @[Composable ReadOnlyComposable] get() = MaterialTheme.typography

    public val shapes: Shapes
        @[Composable ReadOnlyComposable] get() = LocalShapes.current
}
