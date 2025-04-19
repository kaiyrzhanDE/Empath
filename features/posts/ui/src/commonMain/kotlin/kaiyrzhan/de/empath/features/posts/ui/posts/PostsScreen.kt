package kaiyrzhan.de.empath.features.posts.ui.posts

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
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostCard
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostShimmerCard
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PostsScreen(
    component: PostsComponent,
    modifier: Modifier = Modifier,
) {
    val posts = component.posts.collectAsLazyPagingItems()
    val state = component.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    SingleEventEffect(component.action) { action ->
        when (action) {
            is PostsAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    PostsScreen(
        modifier = modifier,
        state = state.value,
        posts = posts,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostsScreen(
    modifier: Modifier = Modifier,
    state: PostsState,
    posts: LazyPagingItems<PostUi>,
    onEvent: (PostsEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(PostsEvent.PostCreateClick) },
                shape = EmpathTheme.shapes.medium,
                containerColor = EmpathTheme.colors.primaryContainer,
                contentColor = EmpathTheme.colors.onPrimaryContainer,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_stylus),
                    contentDescription = "Create post action",
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .background(EmpathTheme.colors.surfaceDim)
                .padding(contentPadding)
                .screenHorizontalPadding(PaddingType.MAIN),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (val refreshState = posts.loadState.refresh) {
                is LoadState.Error -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = refreshState.error.message.orEmpty(),
                        onTryAgainClick = posts::refresh,
                    )
                }

                else -> {
                    OutlinedTextField(
                        modifier = Modifier.defaultMaxWidth(),
                        value = state.query.orEmpty(),
                        onValueChange = { query -> onEvent(PostsEvent.PostSearch(query)) },
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
                            if (posts.loadState.refresh is LoadState.Loading) {
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

                    if (posts.itemCount != 0) {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(posts.itemCount) { index ->
                                val post = posts[index]
                                if (post != null) {
                                    PostCard(
                                        post = post,
                                        userId = state.userId,
                                        onEvent = onEvent,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                } else {
                                    PostShimmerCard(
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            }

                            postsAppendState(
                                posts = posts,
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

private fun LazyListScope.postsAppendState(
    posts: LazyPagingItems<PostUi>,
) {
    when (val appendState = posts.loadState.append) {
        is LoadState.Error -> {
            item {
                ErrorCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    message = appendState.error.message.orEmpty(),
                    onTryAgainClick = posts::retry,
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