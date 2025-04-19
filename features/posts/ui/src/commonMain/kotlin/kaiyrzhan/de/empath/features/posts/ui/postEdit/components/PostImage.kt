package kaiyrzhan.de.empath.features.posts.ui.postEdit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_close
import empath.core.uikit.generated.resources.ic_error_filled
import empath.core.uikit.generated.resources.ic_image_placeholder
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditEvent
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun PostImage(
    modifier: Modifier = Modifier,
    image: ImageUi,
    index: Int,
    onEvent: (PostEditEvent) -> Unit,
) {
    val postImagePainter = rememberImagePainter(
        model = image.platformFile ?: image.imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        placeholder = painterResource(Res.drawable.ic_image_placeholder),
        fallback = painterResource(Res.drawable.ic_image_placeholder),
        filterQuality = FilterQuality.High,
    )
    val imageState = postImagePainter.state.collectAsState()

    Box(
        modifier = modifier
            .clip(EmpathTheme.shapes.medium)
            .background(EmpathTheme.colors.surfaceContainer),
    ) {
        if (image.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerLoading(2000),
            )
        } else {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                        Modifier.clickable { postImagePainter.restart() }
                    },
                painter = postImagePainter,
                contentDescription = "Post Image",
            )
        }
        if (image.isLoading.not()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(EmpathTheme.shapes.small)
                    .clickable { onEvent(PostEditEvent.PostImageRemove(image)) }
                    .background(EmpathTheme.colors.surface)
                    .padding(
                        horizontal = 4.dp,
                        vertical = 2.dp,
                    ),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = "Remove post image",
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd),
            shape = EmpathTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                        vertical = 2.dp,
                    ),
                text = index.plus(1).toGroupedString(),
                style = EmpathTheme.typography.bodySmall,
            )
        }
    }
}