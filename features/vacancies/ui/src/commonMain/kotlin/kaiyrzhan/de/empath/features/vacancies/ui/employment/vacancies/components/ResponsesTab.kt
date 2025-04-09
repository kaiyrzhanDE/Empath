package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.MessageScreen
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus

@Composable
internal fun ResponsesTab(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    responses: LazyPagingItems<VacancyUi>,
    onEvent: (VacanciesEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .background(EmpathTheme.colors.surfaceDim)
            .screenHorizontalPadding(PaddingType.MAIN),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val refreshState = responses.loadState.refresh) {
            is LoadState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = refreshState.error.message.orEmpty(),
                    onTryAgainClick = responses::refresh,
                )
            }

            else -> {
                Spacer(modifier = Modifier.height(12.dp))
                if (responses.itemCount != 0) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(responses.itemCount) { index ->
                            val response = responses[index]

                            if (response != null) {
                                VacancyCard(
                                    vacancy = response,
                                    onEvent = onEvent,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            } else {
                                ResponseShimmerCard(
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                        responsesAppendState(
                            vacancies = responses,
                        )
                    }
                } else {
                    MessageScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

private fun LazyListScope.responsesAppendState(
    vacancies: LazyPagingItems<VacancyUi>,
) {
    when (val appendState = vacancies.loadState.append) {
        is LoadState.Error -> {
            item {
                ErrorCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    message = appendState.error.message.orEmpty(),
                    onTryAgainClick = vacancies::retry,
                )
            }
        }

        is LoadState.Loading -> {
            item {
                CircularLoadingCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }

        is LoadState.NotLoading -> Unit
    }
}


