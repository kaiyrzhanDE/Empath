package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun ResponseShimmerCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .clip(EmpathTheme.shapes.small)
                .shimmerLoading(),
        )
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .clip(EmpathTheme.shapes.small)
                .shimmerLoading(),
        )
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(300.dp)
                .clip(EmpathTheme.shapes.small)
                .shimmerLoading(),
        )
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(400.dp)
                .clip(EmpathTheme.shapes.small)
                .shimmerLoading(),
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(100.dp)
                    .clip(EmpathTheme.shapes.small)
                    .shimmerLoading(),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(100.dp)
                    .clip(EmpathTheme.shapes.small)
                    .shimmerLoading(),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(100.dp)
                    .clip(EmpathTheme.shapes.small)
                    .shimmerLoading(),
            )
        }
    }
}