package kaiyrzhan.de.empath.core.uikit

import androidx.compose.material3.ColorScheme as M3ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/***
// * /* Text-size styles */
 * --empath--display--large: 57px;
 * --empath--display--medium: 45px;
 * --empath--display--small: 36px;
 * --empath--headline--large: 32px;
 * --empath--headline--medium: 28px;
 * --empath--headline--small: 24px;
 * --empath--body--large: 16px;
 * --empath--body--medium: 14px;
 * --empath--body--small: 12px;
 * --empath--label--large: 16px;
 * --empath--label--medium: 14px;
 * --empath--label--small: 12px;
 * --empath--title--large: 22px;
 * --empath--title--medium: 18px;
 * --empath--title--small: 16px;
 *
 * /* Effect styles */
 * --empath--elevation--1:  0px 4px 4px rgba(0, 0, 0, 0.25), 0px 4px 4px rgba(0, 0, 0, 0.25);
 * --empath--elevation--2:  0px 4px 4px rgba(0, 0, 0, 0.25), 0px 4px 4px rgba(0, 0, 0, 0.25);
 * --empath--elevation--3:  0px 4px 4px rgba(0, 0, 0, 0.25), 0px 4px 4px rgba(0, 0, 0, 0.25);
 * --empath--elevation--4:  0px 4px 4px rgba(0, 0, 0, 0.25), 0px 4px 4px rgba(0, 0, 0, 0.25);
 * --empath--elevation--5:  0px 4px 4px rgba(0, 0, 0, 0.25), 0px 4px 4px rgba(0, 0, 0, 0.25);
 *
 */


internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }

internal fun lightColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF3063E7),
        onPrimary = Color(0xFFF5F5F5),
        primaryContainer = Color(0xFF3063E7),
        onPrimaryContainer = Color(0xFFF5F5F5),

        secondary = Color(0xFF6669C3),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFF6669C3),
        onSecondaryContainer = Color.White,

        tertiary = Color(0xFF667EB4),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFF5C74A9),
        onTertiaryContainer = Color.White,

        error = Color(0xFFEC362C),
        onError = Color(0xFF43120F),
        errorContainer = Color(0xFFD7251D),
        onErrorContainer = Color(0xFFF9DEDC),

        background = Color(0xFFF9F8FF),
        onBackground = Color(0xFF040407),

        surface = Color(0xFFFAFBFF),
        onSurface = Color(0xFF1B1B20),

        surfaceVariant = Color(0xFFE2E3F1),
        onSurfaceVariant = Color(0xFF45464F),

        outline = Color(0xFFAEB1BE),
        outlineVariant = Color(0xFFDADDE7),

        shadow = Color.Black,
        scrim = Color.Black,

        inverseSurface = Color(0xFF101017),
        inverseOnSurface = Color(0xFFEAEBF8),
        inversePrimary = Color(0xFF4E8DDA),

        surfaceDim = Color(0xFFE1E3EF),
        surfaceBright = Color.White,
        surfaceTint = Color(0xFFEFF2FC),

        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color(0xFFEFF1F6),
        surfaceContainer = Color(0xFFE9ECF3),
        surfaceContainerHigh = Color(0xFFE3E3EE),
        surfaceContainerHighest = Color(0xFFDADCE8),

        onSurfaceDim = Color(0xFF535865),
        outlineDim = Color(0xFF8288A1),
        inverseOutline = Color(0xFF3C415D),
        inverseOnSurfaceVariant = Color(0xFF9C9CBA),
        inverseOnSurfaceDim = Color(0xFF5F6379),

        onScrim = Color.White,
    )
}

internal fun darkColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF3063E7),
        onPrimary = Color(0xFFF5F5F5),
        primaryContainer = Color(0xFF3063E7),
        onPrimaryContainer = Color(0xFFF5F5F5),

        secondary = Color(0xFF6669C3),
        onSecondary = Color(0xFFF5F5F5),
        secondaryContainer = Color(0xFF6669C3),
        onSecondaryContainer = Color(0xFFF5F5F5),

        tertiary = Color(0xFF667EB4),
        onTertiary = Color(0xFFF5F5F5),
        tertiaryContainer = Color(0xFF667EB4),
        onTertiaryContainer = Color.White,

        error = Color(0xFFFC8079),
        onError = Color(0xFF601410),
        errorContainer = Color(0xFFD7251D),
        onErrorContainer = Color(0xFFF9DEDC),

        background = Color.Black,
        onBackground = Color.White,

        surface = Color(0xFF181927),
        onSurface = Color(0xFFF0EDEE),

        surfaceVariant = Color(0xFF313848),
        onSurfaceVariant = Color(0xFF777A93),

        outline = Color(0xFF3B3E53),
        outlineVariant = Color(0xFF2E303F),

        shadow = Color.Black,
        scrim = Color.Black,

        inverseSurface = Color.White,
        inverseOnSurface = Color(0xFF252730),
        inversePrimary = Color(0xFF6686DE),

        surfaceDim = Color.Black,
        surfaceBright = Color(0xFF4C4B63),
        surfaceTint = Color(0xFF17181F),

        surfaceContainerLowest = Color(0xFF0C0D12),
        surfaceContainerLow = Color(0xFF11121C),
        surfaceContainer = Color(0xFF27283B),
        surfaceContainerHigh = Color(0xFF34374B),
        surfaceContainerHighest = Color(0xFF42475A),

        onSurfaceDim = Color(0xFF5E6370),
        outlineDim = Color(0xFF121319),
        inverseOutline = Color(0xFFDCDDE6),
        inverseOnSurfaceVariant = Color(0xFF707180),
        inverseOnSurfaceDim = Color(0xFF79809E),

        onScrim = Color.White,
    )
}

@Immutable
data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val shadow: Color,
    val scrim: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val surfaceDim: Color,
    val surfaceBright: Color,
    val surfaceTint: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val onSurfaceDim: Color,
    val outlineDim: Color,
    val inverseOutline: Color,
    val inverseOnSurfaceVariant: Color,
    val inverseOnSurfaceDim: Color,
    val onScrim: Color
)

internal fun ColorScheme.toM3ColorScheme(): M3ColorScheme {
    return M3ColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
        surfaceBright = surfaceBright,
        surfaceDim = surfaceDim,
        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surfaceContainerHigh,
        surfaceContainerHighest = surfaceContainerHighest,
        surfaceContainerLow = surfaceContainerLow,
        surfaceContainerLowest = surfaceContainerLowest,
    )
}


