package kaiyrzhan.de.empath.features.posts.ui.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.EmptyResultScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostCard
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostShimmerCard
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsFiltersState
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PostsScreen(
    component: PostsComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val filtersState = component.filtersState.collectAsState()

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
        filtersState = filtersState.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostsScreen(
    modifier: Modifier = Modifier,
    filtersState: PostsFiltersState,
    state: PostsState,
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
            Spacer(modifier = Modifier.height(16.dp))
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
                    value = filtersState.filters.query,
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
                        if (state is PostsState.Loading) {
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
                        .clickable { onEvent(PostsEvent.PostFiltersClick) }
                        .background(EmpathTheme.colors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_tune),
                        contentDescription = null,
                        tint = EmpathTheme.colors.primary,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.Bottom)
                        .clip(EmpathTheme.shapes.small)
                        .clickable { onEvent(PostsEvent.FavouritePostsClick) }
                        .background(EmpathTheme.colors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            resource = if(filtersState.filters.postReactionType == PostReactionType.LIKED) {
                                Res.drawable.ic_favourite_filled
                            } else {
                                Res.drawable.ic_favourite
                            }
                        ),
                        contentDescription = null,
                        tint = EmpathTheme.colors.primary,
                    )
                }
            }
            when (state) {
                is PostsState.Success -> {
                    if (state.posts.isNotEmpty()) {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = PaddingType.MAIN.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(state.posts) { post ->
                                if (post != null) {
                                    PostCard(
                                        post = post,
                                        userId = filtersState.userId,
                                        onEvent = onEvent,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                } else {
                                    PostShimmerCard(
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            }
                        }
                    } else {
                        EmptyResultScreen(
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                is PostsState.Error -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = state.message,
                        onTryAgainClick = { onEvent(PostsEvent.LoadPosts) },
                    )
                }

                is PostsState.Loading -> {
                    CircularLoadingScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is PostsState.Initial -> Unit

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}