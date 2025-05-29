package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.extensions.appendPercent
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

/**
 * weight can be between 0.0 to 1.0
 */

internal fun Double.format(): String {
    val percentStr = (this * 100).toString()
    val parts = percentStr.split(".")
    val integerPart = parts[0]
    val fractionalPart = parts.getOrNull(1)?.take(2).orEmpty().padEnd(2, '0')
    return "$integerPart.$fractionalPart"
}


@Composable
internal fun ProgressIndicator(
    weight: Double,
) {
    val clampedWeight = weight.coerceIn(0.0, 1.0)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
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
                Card(
                    modifier = Modifier.width(progressWidth),
                    shape = EmpathTheme.shapes.extraSmall,
                    colors = CardDefaults.cardColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = buildString {
                            append(clampedWeight.format())
                            appendPercent()
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            if (clampedWeight > 0.0 && clampedWeight < 1.0) {
                Box(
                    modifier = Modifier
                        .width(indicatorWidth)
                        .height(32.dp)
                        .clip(EmpathTheme.shapes.small)
                        .background(EmpathTheme.colors.onSurface)
                )
            }

            if (clampedWeight < 1.0) {
                Card(
                    modifier = Modifier.width(remainingWidth),
                    shape = EmpathTheme.shapes.extraSmall,
                    colors = CardDefaults.cardColors(
                        containerColor = EmpathTheme.colors.surfaceContainer,
                        contentColor = EmpathTheme.colors.onSurface,
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = buildString {
                            append((1.0 - clampedWeight).format())
                            appendPercent()
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
