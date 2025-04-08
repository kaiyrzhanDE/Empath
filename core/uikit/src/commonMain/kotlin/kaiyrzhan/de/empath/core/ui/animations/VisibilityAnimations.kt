package kaiyrzhan.de.empath.core.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
public fun CollapseAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "CollapseAnimatedVisibility",
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        label = label,
        enter = expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(400)
        ) + fadeIn(initialAlpha = 0.3f, animationSpec = tween(400)),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400)),
        content = content,
    )
}