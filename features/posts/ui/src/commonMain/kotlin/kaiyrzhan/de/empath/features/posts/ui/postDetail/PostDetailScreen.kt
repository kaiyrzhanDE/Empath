package kaiyrzhan.de.empath.features.posts.ui.postDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postCreate.components.Header
import kaiyrzhan.de.empath.features.posts.ui.postDetail.components.Post
import kaiyrzhan.de.empath.features.posts.ui.postDetail.components.PostComments
import kaiyrzhan.de.empath.features.posts.ui.postDetail.components.SubPost
import kaiyrzhan.de.empath.features.posts.ui.postDetail.components.TopBar
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailState
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostActions

@Composable
internal fun PostDetailScreen(
    component: PostDetailComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val commentsState = component.commentsState.collectAsState()

    PostDetailScreen(
        modifier = modifier,
        state = state.value,
        commentsState = commentsState.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PostDetailScreen(
    modifier: Modifier,
    state: PostDetailState,
    commentsState: PostCommentsState,
    onEvent: (PostDetailEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surfaceContainerLow,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is PostDetailState.Success -> {
                SelectionContainer {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(contentPadding)
                            .screenHorizontalPadding(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            val maxHeight = maxWidth * 0.15f
                            Header(
                                modifier = Modifier.fillMaxWidth()
                                    .heightIn(min = 40.dp, max = 100.dp)
                                    .height(maxHeight),
                                imageUrl = state.post.author.imageUrl,
                                nickname = state.post.author.nickname,
                                fullName = state.post.author.fullName,
                            )
                        }
                        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                        Post(
                            modifier = Modifier.fillMaxWidth(),
                            post = state.post,
                        )
                        state.post.subPosts.forEach { subPost ->
                            SubPost(
                                modifier = Modifier.fillMaxWidth(),
                                subPost = subPost,
                            )
                        }
                        PostActions(
                            modifier = Modifier.fillMaxWidth(),
                            post = state.post,
                            onLikeClick = { onEvent(PostDetailEvent.PostLikeClick) },
                            onDislikeClick = { onEvent(PostDetailEvent.PostDislikeClick) },
                            onShareClick = { onEvent(PostDetailEvent.PostShare) },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PostComments(
                            modifier = Modifier.fillMaxWidth(),
                            state = commentsState,
                            onEvent = onEvent,
                        )
                    }
                }
            }

            is PostDetailState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    message = state.message,
                    onTryAgainClick = { onEvent(PostDetailEvent.ReloadPost) },
                )
            }

            is PostDetailState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                )
            }

            is PostDetailState.Initial -> Unit
        }
    }
}



