package kaiyrzhan.de.empath.core.ui.animations

import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isBack
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimator
import kotlin.math.absoluteValue

private const val DEFAULT_ANIMATION_DURATION = 400

public fun swipe(duration: Int = DEFAULT_ANIMATION_DURATION): StackAnimator {
    return stackAnimator(
        animationSpec = tween(durationMillis = duration)
    ) { factor, direction, content ->
        content(
            Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    val width = placeable.width
                    layout(width, placeable.height) {
                        placeable.placeRelative(
                            x = if (direction.isBack) {
                                (width * 0.25f * factor).toInt()
                            } else {
                                (width * factor).toInt()
                            },
                            y = 0
                        )
                    }
                }
                .drawWithContent {
                    drawContent()
                    drawRect(
                        color = if (direction.isBack) {
                            Color.Black.copy(alpha = (factor * 0.25f).absoluteValue)
                        } else {
                            Color.Transparent
                        }
                    )
                }
        )
    }
}
