package kaiyrzhan.de.empath.features.articles.ui.articles.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_error_filled
import empath.core.uikit.generated.resources.ic_image_placeholder
import kaiyrzhan.de.empath.core.ui.extensions.appendSlash
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.wideRatio
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ArticleImages(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    if (imageUrls.isNotEmpty()) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            imageUrls.forEachIndexed { index, imageUrl ->
                val articleImagePainter = rememberImagePainter(
                    model = imageUrl,
                    error = painterResource(Res.drawable.ic_error_filled),
                    placeholder = painterResource(Res.drawable.ic_image_placeholder),
                    fallback = painterResource(Res.drawable.ic_image_placeholder),
                    filterQuality = FilterQuality.High,
                )
                Box(
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .clip(EmpathTheme.shapes.medium),
                ) {
                    Image(
                        modifier = Modifier.wideRatio(),
                        painter = articleImagePainter,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Article Image",
                    )
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 8.dp, end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EmpathTheme.colors.surface,
                            contentColor = EmpathTheme.colors.onSurface,
                        ),
                        shape = EmpathTheme.shapes.small,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            text = buildString {
                                append(index)
                                appendSlash()
                                append(imageUrls.size)
                            },
                            style = EmpathTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}
