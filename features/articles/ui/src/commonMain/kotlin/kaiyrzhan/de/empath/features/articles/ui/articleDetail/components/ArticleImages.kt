package kaiyrzhan.de.empath.features.articles.ui.articleDetail.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.wideRatio
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun ArticleImages(
    modifier: Modifier = Modifier,
    imageUrls: List<String>,
) {
    if (imageUrls.isNotEmpty()) {
        val scrollState = rememberScrollState()
        BoxWithConstraints {
            val height = maxWidth * 9 / 16
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .heightIn(max = 500.dp)
                    .fillMaxWidth()
                    .height(height)
                    .clip(EmpathTheme.shapes.medium),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                imageUrls.forEachIndexed { index, url ->
                    ArticleImage(
                        modifier = Modifier
                            .wideRatio()
                            .clip(EmpathTheme.shapes.medium),
                        imageUrl = url,
                        imagePosition = index,
                        countOfImages = imageUrls.size,
                    )
                }
            }
        }
    }
}