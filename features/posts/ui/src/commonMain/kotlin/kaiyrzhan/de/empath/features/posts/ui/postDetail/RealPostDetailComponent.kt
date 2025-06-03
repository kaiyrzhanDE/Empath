package kaiyrzhan.de.empath.features.posts.ui.postDetail

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendComma
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.AppUtils
import kaiyrzhan.de.empath.core.utils.currentPlatform
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelDislikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelDislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelLikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelLikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DislikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.LikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.LikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.ViewPostUseCase
import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.model.Reaction
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailAction
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.CommentMode
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val onBackClick: (isEdited: Boolean) -> Unit,
) : BaseComponent(componentContext), PostDetailComponent {

    private val getPostUseCase: GetPostUseCase by inject()
    private val deletePostUseCase: DeletePostUseCase by inject()
    private val createCommentUseCase: CreateCommentUseCase by inject()
    private val getCommentsUseCase: GetCommentsUseCase by inject()
    private val deleteCommentUseCase: DeleteCommentUseCase by inject()
    private val editCommentUseCase: EditCommentUseCase by inject()
    private val likePostUseCase: LikePostUseCase by inject()
    private val cancelLikePostUseCase: CancelLikePostUseCase by inject()
    private val dislikePostUseCase: DislikePostUseCase by inject()
    private val cancelDislikePostUseCase: CancelDislikePostUseCase by inject()
    private val viewPostUseCase: ViewPostUseCase by inject()
    private val likeCommentUseCase: LikeCommentUseCase by inject()
    private val cancelLikeCommentUseCase: CancelLikeCommentUseCase by inject()
    private val dislikeCommentUseCase: DislikeCommentUseCase by inject()
    private val cancelDislikeCommentUseCase: CancelDislikeCommentUseCase by inject()
    private val appUtils: AppUtils by inject()

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
            is PostDetailEvent.BackClick -> backClick()
            is PostDetailEvent.PostShare -> sharePost()
            is PostDetailEvent.PostView -> viewPost()

            is PostDetailEvent.CommentChange -> changeComment(event.comment)
            is PostDetailEvent.CommentDelete -> deleteComment(postId, event.commentId)
            is PostDetailEvent.CommentEdit -> editComment(postId)
            is PostDetailEvent.CommentCreate -> createComment(postId)
            is PostDetailEvent.ReloadComments -> loadComments(postId)
            is PostDetailEvent.CommentDislike -> dislikeComment(event.comment)
            is PostDetailEvent.CommentLike -> likeComment(event.comment)
            is PostDetailEvent.CommentReply -> replyComment(event.comment)
            is PostDetailEvent.CommentReplyCancel -> cancelReplyComment()
        }
    }

    private fun backClick() {
        val currentState = state.value
        when (currentState) {
            is PostDetailState.Success -> {
                onBackClick(currentState.changedPost != currentState.originalPost)
            }

            is PostDetailState.Initial,
            is PostDetailState.Loading,
            is PostDetailState.Error -> onBackClick(false)

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
                        repliedComment = null,
                        comments = comments.data.map { comment -> comment.toUi() },
                        comment = TextFieldValue(),
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
                text = currentState.comment.text,
            ).onSuccess {
                commentsState.update {
                    currentState.copy(
                        commentMode = CommentMode.Create,
                        comments = currentState.comments.map { comment ->
                            if (comment.id == commentId) {
                                comment.copy(
                                    text = currentState.comment.text,
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
        val currentState = state.value
        check(currentState is PostDetailState.Success)
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
                    append(currentState.originalPost.title)
                    appendLine()
                    append(getString(Res.string.description))
                    appendColon()
                    appendLine()
                    append(currentState.originalPost.description)
                }
            )
            if (currentPlatform.type.isDesktop()) {
                _action.send(
                    PostDetailAction.ShowSnackbar(
                        message = getString(Res.string.copy_description),
                    )
                )
            }
        }
    }

    private fun viewPost() {
        val currentState = state.value
        check(currentState is PostDetailState.Success)
        coroutineScope.launch {
            viewPostUseCase(postId).onSuccess {
                state.update {
                    currentState.copy(
                        changedPost = currentState.changedPost.copy(
                            isViewed = true,
                        )
                    )
                }
            }
        }
    }


    private fun createComment(postId: String) {
        val currentState = commentsState.value
        check(currentState is PostCommentsState.Success)
        coroutineScope.launch {
            createCommentUseCase(
                postId = postId,
                commentId = currentState.repliedComment?.id,
                text = currentState.comment.text,
            ).onSuccess {
                reloadComments(postId)
                commentsState.update {
                    currentState.copy(
                        comment = TextFieldValue(),
                        repliedComment = null,
                    )
                }
            }.onFailure { error ->
                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }

    private fun likePost() {
        coroutineScope.launch {
            val currentState = state.value
            check(currentState is PostDetailState.Success)
            coroutineScope.launch {
                if (currentState.changedPost.reaction.isLiked()) {
                    cancelLikePostUseCase(currentState.changedPost.id).onSuccess {
                        state.update {
                            currentState.copy(
                                changedPost = currentState.changedPost.copy(
                                    reaction = Reaction.DEFAULT,
                                    likesCount = currentState.changedPost.likesCount - 1
                                ),
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                } else {
                    likePostUseCase(currentState.changedPost.id).onSuccess {
                        state.update {
                            currentState.copy(
                                changedPost = when (currentState.changedPost.reaction) {
                                    Reaction.IS_DISLIKED -> {
                                        currentState.changedPost.copy(
                                            reaction = Reaction.IS_LIKED,
                                            likesCount = currentState.changedPost.likesCount + 1,
                                            dislikesCount = currentState.changedPost.dislikesCount - 1
                                        )
                                    }

                                    else -> {
                                        currentState.changedPost.copy(
                                            reaction = Reaction.IS_LIKED,
                                            likesCount = currentState.changedPost.likesCount + 1
                                        )
                                    }
                                },
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dislikePost() {
        coroutineScope.launch {
            val currentState = state.value
            check(currentState is PostDetailState.Success)
            coroutineScope.launch {
                if (currentState.changedPost.reaction.isDisliked()) {
                    cancelDislikePostUseCase(currentState.changedPost.id).onSuccess {
                        state.update {
                            currentState.copy(
                                changedPost = currentState.changedPost.copy(
                                    reaction = Reaction.DEFAULT,
                                    dislikesCount = currentState.changedPost.dislikesCount - 1,
                                ),
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                } else {
                    dislikePostUseCase(currentState.changedPost.id).onSuccess {
                        state.update {
                            currentState.copy(
                                changedPost = when (currentState.changedPost.reaction) {
                                    Reaction.IS_LIKED -> {
                                        currentState.changedPost.copy(
                                            reaction = Reaction.IS_DISLIKED,
                                            likesCount = currentState.changedPost.likesCount - 1,
                                            dislikesCount = currentState.changedPost.dislikesCount + 1
                                        )
                                    }

                                    else -> {
                                        currentState.changedPost.copy(
                                            reaction = Reaction.IS_DISLIKED,
                                            dislikesCount = currentState.changedPost.dislikesCount + 1
                                        )
                                    }
                                },
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun likeComment(selected: CommentUi) {
        coroutineScope.launch {
            val currentState = commentsState.value
            check(currentState is PostCommentsState.Success)
            coroutineScope.launch {
                if (selected.reaction.isLiked()) {
                    cancelLikeCommentUseCase(selected.id).onSuccess {
                        commentsState.update {
                            currentState.copy(
                                comments = currentState.comments.map { comment ->
                                    if (selected.id == comment.id) {
                                        comment.copy(
                                            reaction = Reaction.DEFAULT,
                                            likesCount = comment.likesCount - 1
                                        )
                                    } else {
                                        comment
                                    }
                                },
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                } else {
                    likeCommentUseCase(selected.id).onSuccess {
                        commentsState.update {
                            currentState.copy(
                                comments = currentState.comments.map { comment ->
                                    if (selected.id == comment.id) {
                                        when (comment.reaction) {
                                            Reaction.IS_DISLIKED -> {
                                                comment.copy(
                                                    reaction = Reaction.IS_LIKED,
                                                    likesCount = comment.likesCount + 1,
                                                    dislikesCount = comment.dislikesCount - 1
                                                )
                                            }

                                            else -> {
                                                comment.copy(
                                                    reaction = Reaction.IS_LIKED,
                                                    likesCount = comment.likesCount + 1
                                                )
                                            }
                                        }
                                    } else {
                                        comment
                                    }
                                }
                            )
                        }
                    }.onFailure { error ->
                        when (error) {
                            is Result.Error.DefaultError -> {
                                _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dislikeComment(selected: CommentUi) {
        coroutineScope.launch {
            val currentState = commentsState.value
            check(currentState is PostCommentsState.Success)
            if (selected.reaction.isDisliked()) {
                cancelDislikeCommentUseCase(selected.id).onSuccess {
                    commentsState.update {
                        currentState.copy(
                            comments = currentState.comments.map { comment ->
                                if (selected.id == comment.id) {
                                    comment.copy(
                                        reaction = Reaction.DEFAULT,
                                        dislikesCount = comment.dislikesCount - 1,
                                    )
                                } else {
                                    comment
                                }
                            }
                        )
                    }
                }.onFailure { error ->
                    when (error) {
                        is Result.Error.DefaultError -> {
                            _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                        }
                    }
                }
            } else {
                dislikeCommentUseCase(selected.id).onSuccess {
                    commentsState.update {
                        currentState.copy(
                            comments = currentState.comments.map { comment ->
                                if(selected.id == comment.id) {
                                    when (selected.reaction) {
                                        Reaction.IS_LIKED -> {
                                            comment.copy(
                                                reaction = Reaction.IS_DISLIKED,
                                                likesCount = comment.likesCount - 1,
                                                dislikesCount = comment.dislikesCount + 1
                                            )
                                        }

                                        else -> {
                                            comment.copy(
                                                reaction = Reaction.IS_DISLIKED,
                                                dislikesCount = comment.dislikesCount + 1
                                            )
                                        }
                                    }
                                } else {
                                    comment
                                }
                            }
                        )
                    }
                }.onFailure { error ->
                    when (error) {
                        is Result.Error.DefaultError -> {
                            _action.send(PostDetailAction.ShowSnackbar(error.toString()))
                        }
                    }
                }
            }
        }
    }


    private fun loadPostDetail(postId: String) {
        state.update { PostDetailState.Loading }
        coroutineScope.launch {
            getPostUseCase(postId).onSuccess { post ->
                val updatedPost = post.toUi()
                state.update {
                    PostDetailState.Success(
                        changedPost = updatedPost,
                        originalPost = updatedPost,
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

    private fun changeComment(comment: TextFieldValue) {
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
                onBackClick(true)
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

    private fun replyComment(comment: CommentUi) {
        commentsState.update { currentState ->
            check(currentState is PostCommentsState.Success)
            val text = buildString {
                append(comment.author.fullName)
                appendComma()
                appendSpace()
            }
            currentState.copy(
                repliedComment = comment,
                comment = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                ),
            )
        }
    }

    private fun cancelReplyComment() {
        commentsState.update { currentState ->
            check(currentState is PostCommentsState.Success)
            currentState.copy(
                repliedComment = null,
                comment = TextFieldValue(),
            )
        }
    }
}