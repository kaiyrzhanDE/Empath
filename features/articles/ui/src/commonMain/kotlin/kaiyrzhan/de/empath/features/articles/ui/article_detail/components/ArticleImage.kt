package kaiyrzhan.de.empath.features.articles.ui.article_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_error_filled
import empath.core.uikit.generated.resources.ic_image_placeholder
import kaiyrzhan.de.empath.core.ui.extensions.appendSlash
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ArticleImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    countOfImages: Int,
    imagePosition: Int,
) {
    val articleImagePainter = rememberImagePainter(
        model = imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        placeholder = painterResource(Res.drawable.ic_image_placeholder),
        fallback = painterResource(Res.drawable.ic_image_placeholder),
        filterQuality = FilterQuality.High,
    )
    val imageState = articleImagePainter.state.collectAsState()

    Box(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surfaceContainer),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .thenIf(imageState.value is AsyncImagePainter.State.Loading) {
                    Modifier.shimmerLoading()
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                    Modifier.clickable { articleImagePainter.restart() }
                },
            painter = articleImagePainter,
            contentScale = ContentScale.Crop,
            contentDescription = "Article Image",
        )
        if (countOfImages > 1) {
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
                    text = buildString {
                        append(imagePosition.plus(1).toGroupedString())
                        appendSlash()
                        append(countOfImages.plus(1).toGroupedString())
                    },
                    style = EmpathTheme.typography.bodySmall,
                )
            }
        }
    }
}