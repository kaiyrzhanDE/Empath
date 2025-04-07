package kaiyrzhan.de.empath.features.articles.ui.articles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun ArticleShimmerCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(EmpathTheme.shapes.full)
                    .shimmerLoading()
            )
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .clip(EmpathTheme.shapes.small)
                    .shimmerLoading(),
            )
        }
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(EmpathTheme.shapes.small)
                .shimmerLoading(),
        )

        val lazyListState = rememberLazyListState()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(5) {
                Box(
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .aspectRatio(1.77f)
                        .clip(EmpathTheme.shapes.small)
                        .shimmerLoading(),
                )
            }
        }
    }
}