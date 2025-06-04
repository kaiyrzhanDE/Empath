package kaiyrzhan.de.empath.features.posts.ui.posts

import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.app_name
import empath.core.uikit.generated.resources.copy_description
import empath.core.uikit.generated.resources.description
import empath.core.uikit.generated.resources.invitation_description
import empath.core.uikit.generated.resources.post_creation_rating_requirement
import empath.core.uikit.generated.resources.share_description
import empath.core.uikit.generated.resources.title
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.AppUtils
import kaiyrzhan.de.empath.core.utils.currentPlatform
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelDislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelLikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.LikePostUseCase
import kaiyrzhan.de.empath.features.posts.ui.model.PostFiltersUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsFiltersState
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kaiyrzhan.de.empath.features.posts.ui.model.Reaction
import kaiyrzhan.de.empath.features.posts.ui.model.getIds
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealPostsComponent(
    componentContext: ComponentContext,
    private val onPostClick: (postId: String) -> Unit,
    private val onPostCreateClick: () -> Unit,
    private val onPostEditClick: (postId: String) -> Unit,
    private val onPostFiltersClick: (postFilters: PostFiltersUi) -> Unit,
) : BaseComponent(componentContext), PostsComponent {

    private val getUserUseCase: GetUserUseCase = get()
    private val getPostsUseCase: GetPostsUseCase = get()
    private val deletePostUseCase: DeletePostUseCase by inject()
    private val likePostUseCase: LikePostUseCase by inject()
    private val cancelLikePostUseCase: CancelLikePostUseCase by inject()
    private val dislikePostUseCase: DislikePostUseCase by inject()
    private val cancelDislikePostUseCase: CancelDislikePostUseCase by inject()
    private val appUtils: AppUtils by inject()

    override val filtersState = MutableStateFlow(
        PostsFiltersState.default()
    )

    @OptIn(FlowPreview::class)
    private val queryFlow = filtersState
        .map { state -> state.filters.query }
        .debounce(500)
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = MutableStateFlow<PostsState>(
        PostsState.default()
    )

    private val _action = Channel<PostsAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostsAction> = _action.receiveAsFlow()

    init {
        loadUser()
        observeQuery()
    }

    override fun onEvent(event: PostsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostsEvent.PostClick -> onPostClick(event.postId)
            is PostsEvent.PostDelete -> deletePost(event.postId)
            is PostsEvent.PostEdit -> onPostEditClick(event.postId)
            is PostsEvent.PostSearch -> searchPost(event.query)
            is PostsEvent.PostLike -> likePost(event.post)
            is PostsEvent.LoadPosts -> loadPosts()
            is PostsEvent.FavouritePostsClick -> showFavouritePosts()
            is PostsEvent.PostFiltersClick -> onPostFiltersClick(filtersState.value.filters)
            is PostsEvent.ReloadPosts -> reloadPosts()
            is PostsEvent.ApplyPostFilters -> applyFilters(event.filters)
            is PostsEvent.PostDislike -> dislikePost(event.post)
            is PostsEvent.PostShare -> sharePost(event.post)
            is PostsEvent.PostCreateClick -> createPost()
        }
    }

    private fun loadPosts(query: String = filtersState.value.filters.query) {
        val filtersState = filtersState.value
        state.update { PostsState.Loading }
        coroutineScope.launch {
            getPostsUseCase(
                query = query,
                isLiked = filtersState.filters.postReactionType.isLiked(),
                isDisliked = filtersState.filters.postReactionType.isDisliked(),
                isViewed = filtersState.filters.postReactionType.isViewed(),
                includeWords = filtersState.filters.includeWords,
                excludeWords = filtersState.filters.excludeWords,
                tagsIds = emptyList(),
                specializationsIds = filtersState.filters.selectedSpecializations.getIds(),
            ).onSuccess { posts ->
                state.update {
                    PostsState.Success(
                        posts = posts.data.map { post -> post.toUi() },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update { PostsState.Error(error.toString()) }
                    }
                }
            }
        }
    }

    private fun createPost() {
        coroutineScope.launch {
            val rating = filtersState.value.userRating
            if (rating != null && rating >= 10) {
                onPostCreateClick()
            } else {
                _action.send(
                    PostsAction.ShowSnackbar(
                        message = getString(Res.string.post_creation_rating_requirement),
                    )
                )
            }
        }
    }

    private fun reloadPosts(query: String = filtersState.value.filters.query) {
        val filtersState = filtersState.value
        coroutineScope.launch {
            getPostsUseCase(
                query = query,
                isLiked = filtersState.filters.postReactionType.isLiked(),
                isDisliked = filtersState.filters.postReactionType.isDisliked(),
                isViewed = filtersState.filters.postReactionType.isViewed(),
                includeWords = filtersState.filters.includeWords,
                excludeWords = filtersState.filters.excludeWords,
                tagsIds = emptyList(),
                specializationsIds = filtersState.filters.selectedSpecializations.getIds(),
            ).onSuccess { posts ->
                state.update {
                    PostsState.Success(
                        posts = posts.data.map { post -> post.toUi() },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update { PostsState.Error(error.toString()) }
                    }
                }
            }
        }
    }

    private fun loadUser() {
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                filtersState.update { currentState ->
                    currentState.copy(
                        userId = user.id,
                        userRating = user.rating,
                    )
                }
            }
        }
    }

    private fun observeQuery() {
        coroutineScope.launch {
            queryFlow.collectLatest { query ->
                loadPosts(query)
            }
        }
    }

    private fun searchPost(query: String) {
        filtersState.update { currentState ->
            currentState.copy(
                filters = currentState.filters.copy(
                    query = query,
                ),
            )
        }
    }

    private fun deletePost(postId: String) {
        coroutineScope.launch {
            deletePostUseCase(postId).onSuccess {
                state.update { currentState ->
                    check(currentState is PostsState.Success)
                    currentState.copy(
                        posts = currentState.posts.filter { post -> post.id != postId }
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

    private fun likePost(selected: PostUi) {
        coroutineScope.launch {
            if (selected.reaction.isLiked()) {
                cancelLikePostUseCase(selected.id).onSuccess {
                    state.update { currentState ->
                        check(currentState is PostsState.Success)
                        currentState.copy(
                            posts = currentState.posts.map { post ->
                                if (post.id == selected.id) {
                                    post.copy(
                                        reaction = Reaction.DEFAULT,
                                        likesCount = post.likesCount - 1
                                    )
                                } else {
                                    post
                                }
                            }
                        )
                    }
                }.onFailure { error ->
                    if (error is Result.Error.DefaultError) {
                        _action.send(PostsAction.ShowSnackbar(error.toString()))
                    }
                }
            } else {
                likePostUseCase(selected.id).onSuccess {
                    state.update { currentState ->
                        check(currentState is PostsState.Success)
                        currentState.copy(
                            posts = currentState.posts.map { post ->
                                if (post.id == selected.id) {
                                    when (selected.reaction) {
                                        Reaction.IS_DISLIKED -> {
                                            post.copy(
                                                reaction = Reaction.IS_LIKED,
                                                likesCount = post.likesCount + 1,
                                                dislikesCount = post.dislikesCount - 1
                                            )
                                        }

                                        else -> {
                                            post.copy(
                                                reaction = Reaction.IS_LIKED,
                                                likesCount = post.likesCount + 1
                                            )
                                        }
                                    }
                                } else {
                                    post
                                }
                            }
                        )
                    }
                }.onFailure { error ->
                    if (error is Result.Error.DefaultError) {
                        _action.send(PostsAction.ShowSnackbar(error.toString()))
                    }
                }
            }
        }
    }


    private fun dislikePost(selected: PostUi) {
        coroutineScope.launch {
            if (selected.reaction.isDisliked()) {
                cancelDislikePostUseCase(selected.id).onSuccess {
                    state.update { currentState ->
                        check(currentState is PostsState.Success)
                        currentState.copy(
                            posts = currentState.posts.map { post ->
                                if (post.id == selected.id) {
                                    post.copy(
                                        reaction = Reaction.DEFAULT,
                                        dislikesCount = post.dislikesCount - 1,
                                    )
                                } else {
                                    post
                                }
                            },
                        )
                    }
                }.onFailure { error ->
                    when (error) {
                        is Result.Error.DefaultError -> {
                            _action.send(PostsAction.ShowSnackbar(error.toString()))
                        }
                    }
                }
            } else {
                dislikePostUseCase(selected.id).onSuccess {
                    state.update { currentState ->
                        check(currentState is PostsState.Success)
                        currentState.copy(
                            posts = currentState.posts.map { post ->
                                if (post.id == selected.id) {
                                    when (selected.reaction) {
                                        Reaction.IS_LIKED -> {
                                            post.copy(
                                                reaction = Reaction.IS_DISLIKED,
                                                likesCount = post.likesCount - 1,
                                                dislikesCount = post.dislikesCount + 1
                                            )
                                        }

                                        else -> {
                                            post.copy(
                                                reaction = Reaction.IS_DISLIKED,
                                                dislikesCount = post.dislikesCount + 1
                                            )
                                        }
                                    }
                                } else {
                                    post
                                }
                            },
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
    }

    private fun sharePost(post: PostUi) {
        coroutineScope.launch {
            appUtils.shareText(
                title = getString(Res.string.app_name),
                text = buildString {
                    append(getString(Res.string.share_description))
                    appendLine()
                    append(getString(Res.string.invitation_description))
                    append(getString(Res.string.title))
                    appendColon()
                    appendLine()
                    append(post.title)
                    appendLine()
                    append(getString(Res.string.description))
                    appendColon()
                    appendLine()
                    append(post.description)
                }
            )
            if (currentPlatform.type.isDesktop()) {
                _action.send(
                    PostsAction.ShowSnackbar(
                        message = getString(Res.string.copy_description),
                    )
                )
            }
        }
    }

    private fun showFavouritePosts() {
        filtersState.update { state ->
            state.copy(
                filters = state.filters.copy(
                    postReactionType = if (state.filters.postReactionType == PostReactionType.LIKED) {
                        PostReactionType.NONE
                    } else {
                        PostReactionType.LIKED
                    },
                ),
            )
        }
        loadPosts()
    }

    private fun applyFilters(filters: PostFiltersUi) {
        filtersState.update { state ->
            state.copy(
                filters = filters
            )
        }
    }
}