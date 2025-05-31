package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyWeightUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun VacancyStatisticsCard(
    modifier: Modifier = Modifier,
    weights: List<VacancyWeightUi>,
    onEvent: (VacancyRecommendationsEvent) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "RotateArrow"
    )
    val itemsCount = animateIntAsState(
        targetValue = if (isExpanded) weights.size else minOf(3, weights.size),
        label = "ItemsVisible"
    )

    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            disabledContainerColor = EmpathTheme.colors.surface,
        ),
        onClick = { isExpanded = isExpanded.not() },
        enabled = weights.size > 3,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(Res.string.vacancy_importance),
                    style = EmpathTheme.typography.headlineMedium,
                    color = EmpathTheme.colors.onSurface,
                )
                if (weights.size > 3) {
                    IconButton(
                        onClick = { isExpanded = isExpanded.not() },
                    ) {
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
            }

            Column(
                modifier = Modifier.animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                weights.take(itemsCount.value).forEach { vacancyWeight ->
                    SimpleProgressIndicator(vacancyWeight = vacancyWeight)
                }
            }
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { onEvent(VacancyRecommendationsEvent.VacancyDetailClick) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.secondaryContainer,
                    contentColor = EmpathTheme.colors.onSecondaryContainer,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.vacancy_detail),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

    }
}