package kaiyrzhan.de.empath.core.ui.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

public fun Modifier.defaultMaxWidth(): Modifier {
    return this.then(
        Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
    )
}

@Composable
public fun Modifier.thenIf(condition: Boolean, thenModifier: @Composable () -> Modifier): Modifier {
    return if (condition) then(thenModifier()) else this
}

public fun Modifier.wideRatio(): Modifier {
    return this.then(
        Modifier
            .aspectRatio(16f / 9f)
            .fillMaxSize()
    )
}

public enum class PaddingType(
    public val dp: Dp,
) {
    DIALOG(24.dp),
    MAIN(12.dp),
    AUTH(24.dp),
    DEFAULT(16.dp),
}

public fun Modifier.screenPadding(
    type: PaddingType = PaddingType.DEFAULT,
): Modifier {
    return this.then(
        Modifier.padding(type.dp)
    )
}

public fun Modifier.screenHorizontalPadding(
    type: PaddingType = PaddingType.DEFAULT,
): Modifier {
    return this.then(
        Modifier.padding(horizontal = type.dp)
    )
}

public fun Modifier.screenVerticalPadding(
    type: PaddingType = PaddingType.DEFAULT,
): Modifier {
    return this.then(
        Modifier.padding(vertical = type.dp)
    )
}


public inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
public fun Modifier.shimmerLoading(
    durationMillis: Int = 1000,
    colors: List<Color> = listOf(
        EmpathTheme.colors.primary.copy(alpha = 0.2f),
        EmpathTheme.colors.primary.copy(alpha = 0.4f),
        EmpathTheme.colors.primary.copy(alpha = 0.6f),
        EmpathTheme.colors.primary.copy(alpha = 0.8f),
        EmpathTheme.colors.primary.copy(alpha = 1.0f),
        EmpathTheme.colors.primary.copy(alpha = 0.8f),
        EmpathTheme.colors.primary.copy(alpha = 0.6f),
        EmpathTheme.colors.primary.copy(alpha = 0.4f),
        EmpathTheme.colors.primary.copy(alpha = 0.2f),
    ),
): Modifier {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )

    return drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(x = translateAnimation.value, y = translateAnimation.value),
                end = Offset(
                    x = translateAnimation.value + 100f,
                    y = translateAnimation.value + 100f
                ),
            )
        )
    }
}