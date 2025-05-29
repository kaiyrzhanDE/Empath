package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.extensions.appendPercent
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyWeightUi

@Composable
internal fun SimpleProgressIndicator(
    vacancyWeight: VacancyWeightUi
) {
    val clampedWeight = vacancyWeight.weight.coerceIn(0.0, 1.0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = buildString {
                append(vacancyWeight.name)
                appendLine()
                append(vacancyWeight.weight.format())
                appendPercent()
            },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = EmpathTheme.colors.onSurface,
        )
        BoxWithConstraints(modifier = Modifier.weight(0.7f)) {
            val indicatorWidth = 4.dp
            val spacing = 4.dp
            val usedSpacing = spacing * 2 + indicatorWidth
            val availableWidth = maxWidth - usedSpacing

            val progressWidth = availableWidth * clampedWeight.toFloat()
            val remainingWidth = availableWidth - progressWidth

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (clampedWeight > 0.0) {
                    Box(
                        modifier = Modifier
                            .width(progressWidth)
                            .height(12.dp)
                            .clip(EmpathTheme.shapes.extraSmall)
                            .background(EmpathTheme.colors.primary),
                    )
                }

                if (clampedWeight > 0.0 && clampedWeight < 1.0) {
                    Box(
                        modifier = Modifier
                            .width(indicatorWidth)
                            .height(12.dp)
                            .clip(EmpathTheme.shapes.small)
                            .background(EmpathTheme.colors.onSurface)
                    )
                }

                if (clampedWeight < 1.0) {
                    Box(
                        modifier = Modifier
                            .width(remainingWidth)
                            .height(12.dp)
                            .clip(EmpathTheme.shapes.extraSmall)
                            .background(EmpathTheme.colors.surfaceContainer),
                    )
                }
            }
        }
    }
}
