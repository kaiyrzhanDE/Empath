package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.MessageScreen
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.currentPlatform
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingConditionCard
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.RecruitmentVacancyActions
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.Tabs
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.VacancyCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.VacancyShimmerCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.VacancyWorkingConditions
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components.VacancyWorkingSkills
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacanciesScreen(
    component: VacanciesComponent,
    modifier: Modifier = Modifier
) {
    val state = component.state.collectAsState()
    val vacancies = component.vacancies.collectAsLazyPagingItems()

    SingleEventEffect(component.action) { action ->
        when (action) {
            else -> Unit //TODO(handle actions)
        }
    }

    VacanciesScreen(
        modifier = modifier,
        state = state.value,
        vacancies = vacancies,
        onEvent = component::onEvent,
    )
}

@Composable
private fun VacanciesScreen(
    modifier: Modifier = Modifier,
    state: VacanciesState,
    vacancies: LazyPagingItems<VacancyUi>,
    onEvent: (VacanciesEvent) -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        state.tabs.size
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(VacanciesEvent.TabChange(pagerState.currentPage))
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(VacanciesEvent.VacancyCreateClick) },
                shape = EmpathTheme.shapes.medium,
                containerColor = EmpathTheme.colors.primaryContainer,
                contentColor = EmpathTheme.colors.onPrimaryContainer,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_person_apron_outlined),
                    contentDescription = "Create vacancy action",
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .background(EmpathTheme.colors.scrim),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Tabs(
                modifier = Modifier
                    .defaultMaxWidth()
                    .screenHorizontalPadding(PaddingType.MAIN),
                state = state,
                selectedTabIndex = pagerState.currentPage,
                onClick = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
            CollapseAnimatedVisibility(
                visible = state.currentTab == Tab.Vacancies,
            ) {
                Row(
                    modifier = Modifier
                        .defaultMaxWidth()
                        .screenHorizontalPadding(PaddingType.MAIN),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                        value = state.query,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { query -> onEvent(VacanciesEvent.VacanciesSearch(query)) },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 1,
                        label = {
                            Text(
                                text = stringResource(Res.string.search),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                        trailingIcon = {
                            if (vacancies.loadState.refresh is LoadState.Loading) {
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
                        },
                    )
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .align(Alignment.Bottom)
                            .clip(EmpathTheme.shapes.small)
                            .clickable { onEvent(VacanciesEvent.VacanciesFiltersClick) }
                            .background(EmpathTheme.colors.surface),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.ic_tune),
                            contentDescription = "Vacancies filters",
                            tint = EmpathTheme.colors.primary,
                        )
                    }
                }
            }
            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState,
                userScrollEnabled = currentPlatform.type.isDesktop().not(),
                pageSpacing = 12.dp,
            ) { index ->
                when (state.tabs[index]) {
                    Tab.Vacancies -> {
                        VacanciesTab(
                            modifier = Modifier.weight(1f),
                            lazyListState = lazyListState,
                            state = state,
                            vacancies = vacancies,
                            onEvent = onEvent,
                        )
                    }

                    Tab.Responses -> {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("Responses")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Composable
internal fun VacanciesTab(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    state: VacanciesState,
    vacancies: LazyPagingItems<VacancyUi>,
    onEvent: (VacanciesEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .background(EmpathTheme.colors.scrim)
            .screenHorizontalPadding(PaddingType.MAIN),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val refreshState = vacancies.loadState.refresh) {
            is LoadState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = refreshState.error.message.orEmpty(),
                    onTryAgainClick = vacancies::refresh,
                )
            }

            else -> {
                Spacer(modifier = Modifier.height(12.dp))
                if (vacancies.itemCount != 0) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(5) {
                            VacancyCard(
                                vacancy = VacancyUi.sample(),
                                onEvent = onEvent,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                        items(5) {
                            VacancyShimmerCard(
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                        items(vacancies.itemCount) { index ->
                            val vacancy = vacancies[index]

                            if (vacancy != null) {
                                VacancyCard(
                                    vacancy = vacancy,
                                    onEvent = onEvent,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            } else {
                                VacancyShimmerCard(
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                        vacanciesAppendState(
                            vacancies = vacancies,
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

private fun LazyListScope.vacanciesAppendState(
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



