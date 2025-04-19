package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_stylus
import empath.core.uikit.generated.resources.ic_tune
import empath.core.uikit.generated.resources.search
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.core.utils.currentPlatform
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components.ResponsesTab
import kaiyrzhan.de.empath.features.vacancies.ui.components.Tabs
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.CvsDialog
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components.VacanciesTab
import kaiyrzhan.de.empath.features.vacancies.ui.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.RecruiterCreateDialog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacanciesScreen(
    component: VacanciesComponent,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val state = component.state.collectAsState()
    val vacancies = component.vacancies.collectAsLazyPagingItems()
    val responses = component.responses.collectAsLazyPagingItems()

    val cvsDialogSlot by component.cvsDialog.subscribeAsState()
    cvsDialogSlot.child?.instance?.also { cvsComponent ->
        CvsDialog(
            component = cvsComponent,
        )
    }


    SingleEventEffect(component.action) { action ->
        when (action) {
            is VacanciesAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    VacanciesScreen(
        modifier = modifier,
        state = state.value,
        vacancies = vacancies,
        responses = responses,
        onEvent = component::onEvent,
    )
}

@Composable
private fun VacanciesScreen(
    modifier: Modifier = Modifier,
    state: VacanciesState,
    vacancies: LazyPagingItems<VacancyUi>,
    responses: LazyPagingItems<VacancyUi>,
    onEvent: (VacanciesEvent) -> Unit,
) {
    val vacanciesLazyListState = rememberLazyListState()
    val responsesLazyListState = rememberLazyListState()

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
                onClick = { onEvent(VacanciesEvent.CvCreateClick) },
                shape = EmpathTheme.shapes.medium,
                containerColor = EmpathTheme.colors.primaryContainer,
                contentColor = EmpathTheme.colors.onPrimaryContainer,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_stylus),
                    contentDescription = "Create cv action",
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .background(EmpathTheme.colors.surfaceDim),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Tabs(
                modifier = Modifier
                    .defaultMaxWidth()
                    .screenHorizontalPadding(PaddingType.MAIN),
                tabs = state.tabs,
                currentTab = state.currentTab,
                selectedTabIndex = pagerState.currentPage,
                onClick = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                    value = state.vacanciesFilters.query,
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
                            lazyListState = vacanciesLazyListState,
                            state = state,
                            vacancies = vacancies,
                            onEvent = onEvent,
                        )
                    }

                    Tab.Responses -> {
                        ResponsesTab(
                            modifier = Modifier.weight(1f),
                            lazyListState = responsesLazyListState,
                            responses = responses,
                            onEvent = onEvent,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

