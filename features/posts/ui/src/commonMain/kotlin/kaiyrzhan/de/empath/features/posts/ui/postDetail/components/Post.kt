package kaiyrzhan.de.empath.features.posts.ui.postDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.extensions.appendDot
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kotlin.text.appendLine

@Composable
internal fun Post(
    modifier: Modifier = Modifier,
    post: PostUi,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = post.title,
            style = EmpathTheme.typography.displayMedium,
        )
        SelectedTags(
            modifier = Modifier.fillMaxWidth(),
            tags = post.tags,
        )
        Text(
            text = post.description,
            style = EmpathTheme.typography.titleSmall,
        )
        Text(
            text = buildAnnotatedString {
                post.subPosts.forEach { subPost ->
                    appendDot()
                    appendSpace()
                    withStyle(
                        style = SpanStyle(textDecoration = TextDecoration.Underline)
                    ) {
                        append(subPost.title)
                    }
                    appendLine()
                }
            },
            style = EmpathTheme.typography.titleMedium,
        )
        PostImages(
            modifier = Modifier.fillMaxWidth(),
            imageUrls = post.imageUrls,
        )
        post.subPosts.forEach { subPost ->
            SubPost(
                modifier = Modifier.fillMaxWidth(),
                subPost = subPost,
            )
        }
    }
}