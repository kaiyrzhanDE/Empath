package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.contact_email
import empath.core.uikit.generated.resources.cv_detail
import empath.core.uikit.generated.resources.dividers_placeholder
import empath.core.uikit.generated.resources.ic_person_apron_outlined
import empath.core.uikit.generated.resources.vacancies
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.MessageScreen
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components.CvCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components.VacancyStatisticsCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsState
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacancyRecommendationsScreen(
    modifier: Modifier = Modifier,
    component: VacancyRecommendationsComponent,
) {
    val state = component.state.collectAsState()

    VacancyRecommendationsScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun VacancyRecommendationsScreen(
    modifier: Modifier = Modifier,
    state: VacancyRecommendationsState,
    onEvent: (VacancyRecommendationsEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surfaceDim,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is VacancyRecommendationsState.Success -> {
                if (state.recommendations.recommendations.isEmpty()) {
                    MessageScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(contentPadding)
                            .fillMaxSize()
                            .screenHorizontalPadding(PaddingType.MAIN),
                        contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            VacancyStatisticsCard(
                                modifier = Modifier.fillMaxWidth(),
                                weights = state.recommendations.weights,
                                onEvent = onEvent,
                            )
                        }

                        item{
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                            ) {
                                HorizontalDivider(modifier = Modifier.weight(1f))
                                Text(
                                    text = stringResource(Res.string.vacancies),
                                    style = EmpathTheme.typography.labelMedium,
                                    color = EmpathTheme.colors.outlineVariant,
                                )
                                HorizontalDivider(modifier = Modifier.weight(1f))
                            }
                        }

                        items(state.recommendations.recommendations) { recommendation ->
                            CvCard(
                                modifier = Modifier.fillMaxWidth(),
                                cv = recommendation,
                                onEvent = onEvent,
                            )
                        }
                    }
                }
            }

            is VacancyRecommendationsState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is VacancyRecommendationsState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = {
                        onEvent(VacancyRecommendationsEvent.LoadVacancyRecommendations)
                    },
                )
            }

            is VacancyRecommendationsState.Initial -> Unit
        }

    }
}