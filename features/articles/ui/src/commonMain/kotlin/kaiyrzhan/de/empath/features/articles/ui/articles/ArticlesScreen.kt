package kaiyrzhan.de.empath.features.articles.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.MessageScreen
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.articles.ui.articles.components.ArticleCard
import kaiyrzhan.de.empath.features.articles.ui.articles.components.ArticleShimmerCard
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesAction
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesEvent
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesState
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ArticlesScreen(
    component: ArticlesComponent,
    modifier: Modifier = Modifier,
) {
    val articles = component.articles.collectAsLazyPagingItems()
    val state = component.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    SingleEventEffect(component.action) { action ->
        when (action) {
            is ArticlesAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    ArticlesScreen(
        modifier = modifier,
        state = state.value,
        articles = articles,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticlesScreen(
    modifier: Modifier = Modifier,
    state: ArticlesState,
    articles: LazyPagingItems<ArticleUi>,
    onEvent: (ArticlesEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ArticlesEvent.ArticleCreateClick) },
                shape = EmpathTheme.shapes.medium,
                containerColor = EmpathTheme.colors.primaryContainer,
                contentColor = EmpathTheme.colors.onPrimaryContainer,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_stylus),
                    contentDescription = "Create article action",
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .background(EmpathTheme.colors.scrim)
                .padding(contentPadding)
                .screenHorizontalPadding(PaddingType.MAIN),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (val refreshState = articles.loadState.refresh) {
                is LoadState.Error -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = refreshState.error.message.orEmpty(),
                        onTryAgainClick = articles::refresh,
                    )
                }

                else -> {
                    OutlinedTextField(
                        modifier = Modifier.defaultMaxWidth(),
                        value = state.query.orEmpty(),
                        onValueChange = { query -> onEvent(ArticlesEvent.ArticleSearch(query)) },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        shape = EmpathTheme.shapes.small,
                        maxLines = 1,
                        label = {
                            Text(
                                text = stringResource(Res.string.search),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                        trailingIcon = {
                            if (articles.loadState.refresh is LoadState.Loading) {
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

                    if (articles.itemCount != 0) {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(articles.itemCount) { index ->
                                val article = articles[index]
                                if (article != null) {
                                    ArticleCard(
                                        article = article,
                                        userId = state.userId,
                                        onEvent = onEvent,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                } else {
                                    ArticleShimmerCard(
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            }

                            articlesAppendState(
                                articles = articles,
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
}

private fun LazyListScope.articlesAppendState(
    articles: LazyPagingItems<ArticleUi>,
) {
    when (val appendState = articles.loadState.append) {
        is LoadState.Error -> {
            item {
                ErrorCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    message = appendState.error.message.orEmpty(),
                    onTryAgainClick = articles::retry,
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