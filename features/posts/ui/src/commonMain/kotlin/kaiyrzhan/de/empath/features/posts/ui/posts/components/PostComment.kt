package kaiyrzhan.de.empath.features.posts.ui.posts.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.CommentNode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RenderCommentTree(
    comments: List<CommentNode>,
    level: Int = 0,
    onReplyClick: (CommentUi) -> Unit,
    onLikeClick: (CommentUi) -> Unit,
    onDislikeClick: (CommentUi) -> Unit,
    isLast: Boolean = true,
) {
    comments.forEachIndexed { index, comment ->
        val isLastComment = index == comments.lastIndex
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (level > 0) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(IntrinsicSize.Min),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(1.dp)
                            .weight(1f)
                            .background(color = EmpathTheme.colors.onSurfaceVariant),
                    )
                    if (comment.replies.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(EmpathTheme.colors.onSurfaceVariant),
                        )
                        if (isLastComment) {
                            Box(
                                modifier = Modifier
                                    .weight(1f),
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .width(1.dp)
                                    .weight(1f)
                                    .background(color = EmpathTheme.colors.onSurfaceVariant),
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PostComment(
                    comment = comment.comment,
                    onReplyClick = { onReplyClick(comment.comment) },
                    onLikeClick = { onLikeClick(comment.comment) },
                    onDislikeClick = { onDislikeClick(comment.comment) }
                )
                if (comment.replies.isNotEmpty()) {
                    RenderCommentTree(
                        comments = comment.replies,
                        level = level + 1,
                        onReplyClick = onReplyClick,
                        onLikeClick = onLikeClick,
                        onDislikeClick = onDislikeClick,
                        isLast = isLastComment,
                    )
                }
            }
        }
    }
}

@Composable
internal fun PostComment(
    modifier: Modifier = Modifier,
    comment: CommentUi,
    onReplyClick: (() -> Unit)? = null,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
) {
    val authorImagePainter = rememberImagePainter(
        model = comment.author.imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        placeholder = painterResource(Res.drawable.ic_account_circle),
        fallback = painterResource(Res.drawable.ic_account_circle),
        filterQuality = FilterQuality.High,
    )
    val imageState = authorImagePainter.state.collectAsState()


    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surfaceContainer)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                modifier = Modifier
                    .clip(EmpathTheme.shapes.full)
                    .size(36.dp)
                    .fillMaxSize()
                    .thenIf(imageState.value is AsyncImagePainter.State.Loading) {
                        Modifier.shimmerLoading()
                    }
                    .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                        Modifier.clickable { authorImagePainter.restart() }
                    }
                    .thenIf(imageState.value is AsyncImagePainter.State.Success) {
                        Modifier.border(
                            width = 1.dp,
                            color = EmpathTheme.colors.onSurfaceVariant,
                            shape = EmpathTheme.shapes.full,
                        )
                    },
                painter = authorImagePainter,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = comment.author.nickname,
                    style = EmpathTheme.typography.labelSmall,
                    color = EmpathTheme.colors.onSurface,
                )

                Text(
                    text = comment.author.fullName,
                    style = EmpathTheme.typography.labelSmall,
                    color = EmpathTheme.colors.onSurfaceVariant,
                )
            }
            Text(
                text = "1 year ago",
                style = EmpathTheme.typography.labelSmall,
                color = EmpathTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
        Text(
            text = comment.text,
            style = EmpathTheme.typography.bodyLarge,
            color = EmpathTheme.colors.onSurface,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
        )
        if (onReplyClick != null) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PostAction(
                    text = comment.likesCount.toGroupedString(),
                    painter = painterResource(Res.drawable.ic_sentiment_satisfied),
                    isChecked = comment.reaction.isLiked(),
                    onClick = onLikeClick,
                )
                PostAction(
                    text = comment.dislikesCount.toGroupedString(),
                    painter = painterResource(Res.drawable.ic_sentiment_dissatisfied),
                    isChecked = comment.reaction.isDisliked(),
                    onClick = onDislikeClick,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .clip(EmpathTheme.shapes.small)
                        .background(EmpathTheme.colors.surfaceContainerHigh)
                        .clickable(onClick = onReplyClick)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.reply),
                        style = EmpathTheme.typography.labelMedium,
                        color = EmpathTheme.colors.onSurface,
                    )
                }
            }
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
            .background(EmpathTheme.colors.surfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(animatedIconColor.value)
        )
        Text(
            text = text,
            style = EmpathTheme.typography.labelMedium,
            color = EmpathTheme.colors.onSurface,
        )
    }
}