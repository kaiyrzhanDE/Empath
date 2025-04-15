package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_arrow_forward
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.extensions.appendRequiredMarker
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun <T> ColumnScope.Filters(
    modifier: Modifier = Modifier,
    filters: List<T>,
    title: String,
    leadingPainter: Painter,
    onSelect: (T) -> Unit,
    isSelected: (T) -> Boolean,
    anySelected: (filters: List<T>) -> Boolean,
    label: @Composable (T) -> String,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "RotateArrow"
    )

    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (anySelected(filters) && isExpanded.not()) EmpathTheme.colors.primary else EmpathTheme.colors.outlineVariant,
        ),
        onClick = { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surfaceContainer,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = { isExpanded = isExpanded.not() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = leadingPainter,
                    contentDescription = title,
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }

            Text(
                text = buildAnnotatedString {
                    append(title)
                    appendRequiredMarker()
                },
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotateAnimation),
                    painter = painterResource(Res.drawable.ic_arrow_forward),
                    contentDescription = title,
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }
        }
    }

    CollapseAnimatedVisibility(visible = isExpanded) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = EmpathTheme.shapes.small,
            border = BorderStroke(1.dp, EmpathTheme.colors.outline),
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                filters.forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RadioButton(
                            selected = isSelected(filter),
                            onClick = { onSelect(filter) },
                        )
                        Text(
                            text = label(filter),
                            style = EmpathTheme.typography.bodyLarge,
                            color = EmpathTheme.colors.onSurface,
                        )
                    }
                }
            }
        }
    }
}
