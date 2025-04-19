package kaiyrzhan.de.empath.features.posts.ui.postDetail

import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailAction
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.CommentMode
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject

internal class RealPostDetailComponent(
    componentContext: ComponentContext,
    private val postId: String,
    private val onPostEditClick: () -> Unit,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), PostDetailComponent {

    private val getPostUseCase: GetPostUseCase by inject()
    private val deletePostUseCase: DeletePostUseCase by inject()
    private val createCommentUseCase: CreateCommentUseCase by inject()
    private val getCommentsUseCase: GetCommentsUseCase by inject()
    private val deleteCommentUseCase: DeleteCommentUseCase by inject()
    private val editCommentUseCase: EditCommentUseCase by inject()

    override val state = MutableStateFlow<PostDetailState>(
        PostDetailState.default()
    )

    override val commentsState = MutableStateFlow<PostCommentsState>(
        PostCommentsState.default()
    )

    private val _action = Channel<PostDetailAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostDetailAction> = _action.receiveAsFlow()

    init {
        loadPostDetail(postId)
        loadComments(postId)
    }

    override fun onEvent(event: PostDetailEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostDetailEvent.PostLikeClick -> likePost()
            is PostDetailEvent.PostDislikeClick -> dislikePost()
            is PostDetailEvent.ReloadPost -> loadPostDetail(postId)
            is PostDetailEvent.DeletePost -> deletePost()
            is PostDetailEvent.EditPost -> onPostEditClick()
            is PostDetailEvent.BackClick -> onBackClick()
            is PostDetailEvent.PostShare -> sharePost()

            is PostDetailEvent.CommentChange -> changeComment(event.comment)
            is PostDetailEvent.CommentDelete -> deleteComment(postId, event.commentId)
            is PostDetailEvent.CommentEditMode -> editCommentMode(event.commentId)
            is PostDetailEvent.CommentEdit -> editComment(postId)
            is PostDetailEvent.CommentCreate -> createComment(postId)
            is PostDetailEvent.ReloadComments -> loadComments(postId)
        }
    }

    private fun editCommentMode(
        commentId: String,
    ) {
        commentsState.update { currentState ->
            check(currentState is PostCommentsState.Success)
            currentState.copy(
                commentMode = CommentMode.Edit(commentId),
            )
        }
    }

    private fun loadComments(
        postId: String,
    ) {
        commentsState.update { PostCommentsState.Loading }
        coroutineScope.launch {
            getCommentsUseCase(
                postId = postId,
            ).onSuccess { comments ->
                commentsState.update {
                    PostCommentsState.Success(
                        comments = comments.data.map { comment -> comment.toUi() },
                    )
                }
            }.onFailure { error ->
                commentsState.update {
                    PostCommentsState.Error(
                        message = error.toString(),
                    )
                }
            }
        }
    }

    private fun reloadComments(postId: String) {
        val currentState = commentsState.value
        check(currentState is PostCommentsState.Success)
        coroutineScope.launch {
            getCommentsUseCase(
                postId = postId,
            ).onSuccess { comments ->
                commentsState.update {
                    currentState.copy(
                        comments = comments.data.map { comment -> comment.toUi() },
                        comment = "",
                    )
                }
            }.onFailure { error ->
                commentsState.update {
                    PostCommentsState.Error(
                        message = error.toString(),
                    )
                }
            }
        }
    }

    private fun editComment(
        postId: String,
    ) {
        val currentState = commentsState.value
        check(currentState is PostCommentsState.Success)
        val commentId = (currentState.commentMode as? CommentMode.Edit)?.commentId ?: return
        coroutineScope.launch {
            commentsState.update {
                currentState.copy(
                    commentMode = CommentMode.Loading,
                )
            }
            editCommentUseCase(
                postId = postId,
                commentId = currentState.commentMode.commentId,
                text = currentState.comment,
            ).onSuccess {
                commentsState.update {
                    currentState.copy(
                        commentMode = CommentMode.Create,
                        comments = currentState.comments.map { comment ->
                            if (comment.id == commentId) {
                                comment.copy(
                                    text = currentState.comment,
                                )
                            } else {
                                comment
                            }
                        },
                    )
                }
                _action.send(PostDetailAction.ShowSnackbar(getString(Res.string.comment_edited_successfully)))
            }.onFailure { error ->
                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }


    private fun deleteComment(
        postId: String,
        commentId: String,
    ) {
        coroutineScope.launch {
            deleteCommentUseCase(
                postId = postId,
                commentId = commentId,
            ).onSuccess {
                commentsState.update { currentState ->
                    check(currentState is PostCommentsState.Success)
                    currentState.copy(
                        comments = currentState.comments.filter { it.id != commentId }
                    )
                }
            }.onFailure { error ->
                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }

    private fun sharePost() {
        TODO()
    }

    private fun createComment(postId: String) {
        val currentState = commentsState.value
        check(currentState is PostCommentsState.Success)
        coroutineScope.launch {
            createCommentUseCase(
                postId = postId,
                text = currentState.comment,
            ).onSuccess {
                reloadComments(postId)
                commentsState.update {
                    currentState.copy(
                        comment = "",
                    )
                }
            }.onFailure { error ->
                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }

    private fun likePost() {
        state.update { currentState ->
            check(currentState is PostDetailState.Success)
            currentState.copy(
                post = currentState.post.copy(
                    isLiked = true,
                )
            )
        }
    }

    private fun dislikePost() {
        state.update { currentState ->
            check(currentState is PostDetailState.Success)
            currentState.copy(
                post = currentState.post.copy(
                    isLiked = false,
                )
            )
        }
    }

    private fun loadPostDetail(postId: String) {
        state.update { PostDetailState.Loading }
        coroutineScope.launch {
            getPostUseCase(postId).onSuccess { post ->
                state.update {
                    PostDetailState.Success(
                        post = post.toUi(),
                    )
                }
            }.onFailure { error ->
                state.update {
                    PostDetailState.Error(
                        message = getString(Res.string.unknown_error),
                    )
                }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            PostDetailAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

    private fun changeComment(comment: String) {
        commentsState.update { currentState ->
            check(currentState is PostCommentsState.Success)
            currentState.copy(
                comment = comment,
            )
        }
    }

    private fun deletePost() {
        val currentState = state.value
        state.update { PostDetailState.Loading }
        coroutineScope.launch {
            deletePostUseCase(postId).onSuccess {
                _action.send(
                    PostDetailAction.ShowSnackbar(
                        message = getString(Res.string.post_deleted_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            PostDetailAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}