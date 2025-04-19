package kaiyrzhan.de.empath.features.posts.ui.posts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postDetail.components.SelectedTags
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi


@Composable
internal fun PostCard(
    post: PostUi,
    userId: String,
    onEvent: (PostsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surface)
            .noRippleClickable { onEvent(PostsEvent.PostClick(post.id)) }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PostHeader(
            post = post,
            userId = userId,
            modifier = Modifier.fillMaxWidth(),
            onEvent = onEvent,
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
        PostContent(
            modifier = Modifier.fillMaxWidth(),
            title = post.title,
            description = post.description,
            tags = post.tags,
        )
        PostImages(
            imageUrls = post.imageUrls,
            modifier = Modifier.fillMaxWidth(),
        )
        PostActions(
            modifier = Modifier.fillMaxWidth(),
            post = post,
            onLikeClick = { onEvent(PostsEvent.PostLike(post.id)) },
            onShareClick = { onEvent(PostsEvent.PostShare(post.id)) },
            onDislikeClick = { onEvent(PostsEvent.PostDislike(post.id)) },
        )
        PostComment(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PostContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    tags: List<TagUi>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = title,
            style = EmpathTheme.typography.headlineSmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        SelectedTags(
            modifier = Modifier.fillMaxWidth(),
            tags = tags,
        )
        Text(
            text = description,
            style = EmpathTheme.typography.titleSmall,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}




