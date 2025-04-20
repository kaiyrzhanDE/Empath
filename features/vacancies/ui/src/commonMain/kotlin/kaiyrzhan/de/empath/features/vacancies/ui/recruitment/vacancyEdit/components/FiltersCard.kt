package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_arrow_forward
import empath.core.uikit.generated.resources.ic_error_filled
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyFilterState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ColumnScope.FiltersCard(
    modifier: Modifier = Modifier,
    state: VacancyFilterState,
    title: AnnotatedString,
    leadingPainter: Painter,
    onSelect: (SkillUi) -> Unit,
    isSelected: (SkillUi) -> Boolean,
    onReload: () -> Unit,
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
            color = when (state) {
                is VacancyFilterState.Error -> EmpathTheme.colors.error
                is VacancyFilterState.Loading -> EmpathTheme.colors.primary
                is VacancyFilterState.Initial -> EmpathTheme.colors.outlineVariant
                is VacancyFilterState.Success -> {
                    if (state.filters.any { it.isSelected } && isExpanded.not()) EmpathTheme.colors.primary
                    else EmpathTheme.colors.outlineVariant
                }
            }
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
                    contentDescription = null,
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }

            Text(
                text = title,
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))

            when (state) {
                is VacancyFilterState.Success -> {
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(rotateAnimation),
                            painter = painterResource(Res.drawable.ic_arrow_forward),
                            contentDescription = null,
                            tint = EmpathTheme.colors.onSurfaceVariant,
                        )
                    }
                }

                is VacancyFilterState.Loading -> {
                    Box(
                        modifier = Modifier.size(40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            trackColor = EmpathTheme.colors.secondary,
                            strokeCap = StrokeCap.Square,
                            color = EmpathTheme.colors.primary,
                        )
                    }
                }

                is VacancyFilterState.Error -> {
                    Box(
                        modifier = Modifier.size(40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.ic_error_filled),
                            contentDescription = null,
                            tint = EmpathTheme.colors.error,
                        )
                    }
                }

                is VacancyFilterState.Initial -> Unit
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
            when (state) {
                is VacancyFilterState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        state.filters.forEach { filter ->
                            Row(
                                modifier = Modifier.noRippleClickable { onSelect(filter) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                RadioButton(
                                    selected = isSelected(filter),
                                    onClick = { onSelect(filter) },
                                )
                                Text(
                                    text = filter.name,
                                    style = EmpathTheme.typography.bodyLarge,
                                    color = EmpathTheme.colors.onSurface,
                                )
                            }
                        }
                    }
                }

                is VacancyFilterState.Loading -> {
                    CircularLoadingCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

                is VacancyFilterState.Error -> {
                    ErrorCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        iconSize = 60.dp,
                        message = state.message,
                        onTryAgainClick = onReload,
                    )
                }

                is VacancyFilterState.Initial -> Unit
            }
        }
    }
}
