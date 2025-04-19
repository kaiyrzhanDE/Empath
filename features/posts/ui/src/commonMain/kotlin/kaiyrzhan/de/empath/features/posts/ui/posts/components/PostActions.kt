package kaiyrzhan.de.empath.features.posts.ui.posts.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun PostActions(
    modifier: Modifier = Modifier,
    category: String = "Post", //TODO("Need to add category")
    post: PostUi,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PostAction(
            text = post.likesCount.toGroupedString(),
            painter = painterResource(Res.drawable.ic_sentiment_satisfied),
            isChecked = post.isLiked,
            contentDescription = "Post like action",
            onClick = onLikeClick,
        )

        PostAction(
            text = post.dislikesCount.toGroupedString(),
            painter = painterResource(Res.drawable.ic_sentiment_dissatisfied),
            isChecked = post.isLiked.not(),
            contentDescription = "Post dislike action",
            onClick = onDislikeClick,
        )

        Row(
            modifier = Modifier
                .clip(EmpathTheme.shapes.small)
                .background(EmpathTheme.colors.surfaceContainer)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.ic_visibility_on),
                contentDescription = "Post views count",
                colorFilter = ColorFilter.tint(EmpathTheme.colors.onSurface)
            )
        }

        PostAction(
            painter = painterResource(Res.drawable.ic_share),
            contentDescription = "Post share action",
            onClick = onShareClick,
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = EmpathTheme.colors.outlineVariant,
        )

        Row(
            modifier = Modifier
                .clip(EmpathTheme.shapes.small)
                .background(EmpathTheme.colors.surfaceContainer)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = category,
                style = EmpathTheme.typography.bodySmall,
                color = EmpathTheme.colors.onSurface,
            )
        }
    }
}

@Composable
private fun PostAction(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    isChecked: Boolean,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    val animatedIconColor = animateColorAsState(
        if (isChecked) EmpathTheme.colors.primary
        else EmpathTheme.colors.onSurface,
    )

    Row(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(animatedIconColor.value)
        )
        Text(
            text = text,
            style = EmpathTheme.typography.bodySmall,
            color = EmpathTheme.colors.onSurface,
        )
    }
}

@Composable
private fun PostAction(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(EmpathTheme.colors.onSurface)
        )
    }
}



