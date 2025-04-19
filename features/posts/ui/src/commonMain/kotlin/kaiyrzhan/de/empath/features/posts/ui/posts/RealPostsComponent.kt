package kaiyrzhan.de.empath.features.posts.ui.posts

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostsUseCase
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealPostsComponent(
    componentContext: ComponentContext,
    private val onPostClick: (postId: String) -> Unit,
    private val onPostCreateClick: () -> Unit,
    private val onPostEditClick: (postId: String) -> Unit,
) : BaseComponent(componentContext), PostsComponent {

    private val getPostsUseCase: GetPostsUseCase = get()
    private val deletePostUseCase: DeletePostUseCase by inject()
    private val getUserUseCase: GetUserUseCase = get()

    override val state = MutableStateFlow(
        PostsState.default()
    )

    @OptIn(FlowPreview::class)
    private val queryFlow = state
        .map { state -> state.query }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val posts: Flow<PagingData<PostUi>> =
        combine(queryFlow, state) { query, state ->
            query to state
        }.flatMapLatest { (query, state) ->
            getPostsUseCase(query)
                .map { pagingData ->
                    pagingData
                        .filter { post -> post.id !in state.removedPostsIds }
                        .map { post ->
                            val id = post.id
                            val isViewed = id in state.viewedPostsIds
                            val isLiked = id in state.likedPostsIds
                            val isDisliked = id in state.dislikedPostsIds

                            post.toUi().copy(
                                isViewed = isViewed,
                                isLiked = isLiked && !isDisliked,
                                viewsCount = post.viewsCount + if (isViewed) 1 else 0,
                                likesCount = post.likesCount + if (isLiked) 1 else 0,
                                dislikesCount = post.dislikesCount + if (isDisliked) 1 else 0,
                            )
                        }
                }
        }.cachedIn(coroutineScope)

    private val _action = Channel<PostsAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostsAction> = _action.receiveAsFlow()

    init {
        loadUser()
    }

    override fun onEvent(event: PostsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostsEvent.PostClick -> onPostClick(event.postId)
            is PostsEvent.PostDelete -> deletePost(event.postId)
            is PostsEvent.PostEdit -> onPostEditClick(event.postId)
            is PostsEvent.PostSearch -> searchPost(event.query)
            is PostsEvent.PostLike -> likePost(event.postId)
            is PostsEvent.PostDislike -> dislikePost(event.postId)
            is PostsEvent.PostShare -> sharePost(event.postId)
            is PostsEvent.PostView -> viewPost(event.postId)
            is PostsEvent.PostCreateClick -> onPostCreateClick()
        }
    }

    private fun loadUser() {
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update { currentState ->
                    currentState.copy(
                        userId = user.id
                    )
                }
            }
        }
    }

    private fun searchPost(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query,
            )
        }
    }

    private fun deletePost(postId: String) {
        coroutineScope.launch {
            deletePostUseCase(postId)
                .onSuccess {
                    state.update { currentState ->
                        currentState.copy(
                            removedPostsIds = currentState.removedPostsIds + postId
                        )
                    }
                }.onFailure { error ->
                    when (error) {
                        is Result.Error.DefaultError -> {
                            _action.send(PostsAction.ShowSnackbar(error.toString()))
                        }
                    }
                }
        }
    }

    private fun likePost(postId: String) {
        state.update { currentState ->
            currentState.copy(
                likedPostsIds = currentState.likedPostsIds + postId,
                dislikedPostsIds = currentState.dislikedPostsIds
                    .minus(postId),
            )
        }
    }

    private fun dislikePost(postId: String) {
        state.update { currentState ->
            currentState.copy(
                dislikedPostsIds = currentState.dislikedPostsIds + postId,
                likedPostsIds = currentState.likedPostsIds
                    .minus(postId),
            )
        }
    }

    private fun viewPost(postId: String) {
        state.update { currentState ->
            currentState.copy(
                viewedPostsIds = currentState.viewedPostsIds + postId,
            )
        }
    }

    private fun sharePost(postId: String) {
        //TODO("Not yet implementataion")
    }
}