package kaiyrzhan.de.empath.features.articles.ui.article_detail

import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.articles.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.DeleteArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleCommentsState
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailAction
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailEvent
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailState
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.CommentMode
import kaiyrzhan.de.empath.features.articles.ui.model.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject

internal class RealArticleDetailComponent(
    componentContext: ComponentContext,
    private val articleId: String,
    private val onArticleEditClick: () -> Unit,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), ArticleDetailComponent {

    private val getArticleUseCase: GetArticleUseCase by inject()
    private val deleteArticleUseCase: DeleteArticleUseCase by inject()
    private val createCommentUseCase: CreateCommentUseCase by inject()
    private val getCommentsUseCase: GetCommentsUseCase by inject()
    private val deleteCommentUseCase: DeleteCommentUseCase by inject()
    private val editCommentUseCase: EditCommentUseCase by inject()

    override val state = MutableStateFlow<ArticleDetailState>(
        ArticleDetailState.default()
    )

    override val commentsState = MutableStateFlow<ArticleCommentsState>(
        ArticleCommentsState.default()
    )

    private val _action = Channel<ArticleDetailAction>(capacity = Channel.BUFFERED)
    override val action: Flow<ArticleDetailAction> = _action.receiveAsFlow()

    init {
        loadArticleDetail(articleId)
        loadComments(articleId)
    }

    override fun onEvent(event: ArticleDetailEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is ArticleDetailEvent.ArticleLikeClick -> likeArticle()
            is ArticleDetailEvent.ArticleDislikeClick -> dislikeArticle()
            is ArticleDetailEvent.ReloadArticle -> loadArticleDetail(articleId)
            is ArticleDetailEvent.DeleteArticle -> deleteArticle()
            is ArticleDetailEvent.EditArticle -> onArticleEditClick()
            is ArticleDetailEvent.BackClick -> onBackClick()
            is ArticleDetailEvent.ArticleShare -> shareArticle()

            is ArticleDetailEvent.CommentChange -> changeComment(event.comment)
            is ArticleDetailEvent.CommentDelete -> deleteComment(articleId, event.commentId)
            is ArticleDetailEvent.CommentEditMode -> editCommentMode(event.commentId)
            is ArticleDetailEvent.CommentEdit -> editComment(articleId)
            is ArticleDetailEvent.CommentCreate -> createComment(articleId)
            is ArticleDetailEvent.ReloadComments -> loadComments(articleId)
        }
    }

    private fun editCommentMode(
        commentId: String,
    ) {
        commentsState.update { currentState ->
            check(currentState is ArticleCommentsState.Success)
            currentState.copy(
                commentMode = CommentMode.Edit(commentId),
            )
        }
    }

    private fun loadComments(
        articleId: String,
    ) {
        commentsState.update { ArticleCommentsState.Loading }
        coroutineScope.launch {
            getCommentsUseCase(
                articleId = articleId,
            ).onSuccess { comments ->
                commentsState.update {
                    ArticleCommentsState.Success(
                        comments = comments.data.map { comment -> comment.toUi() },
                    )
                }
            }.onFailure { error ->
                commentsState.update {
                    ArticleCommentsState.Error(
                        message = error.toString(),
                    )
                }
            }
        }
    }

    private fun reloadComments(articleId: String) {
        val currentState = commentsState.value
        check(currentState is ArticleCommentsState.Success)
        coroutineScope.launch {
            getCommentsUseCase(
                articleId = articleId,
            ).onSuccess { comments ->
                commentsState.update {
                    currentState.copy(
                        comments = comments.data.map { comment -> comment.toUi() },
                        comment = "",
                    )
                }
            }.onFailure { error ->
                commentsState.update {
                    ArticleCommentsState.Error(
                        message = error.toString(),
                    )
                }
            }
        }
    }

    private fun editComment(
        articleId: String,
    ) {
        val currentState = commentsState.value
        check(currentState is ArticleCommentsState.Success)
        val commentId = (currentState.commentMode as? CommentMode.Edit)?.commentId ?: return
        coroutineScope.launch {
            commentsState.update {
                currentState.copy(
                    commentMode = CommentMode.Loading,
                )
            }
            editCommentUseCase(
                articleId = articleId,
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
                _action.send(ArticleDetailAction.ShowSnackbar(getString(Res.string.comment_edited_successfully)))
            }.onFailure { error ->
                _action.send(ArticleDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }


    private fun deleteComment(
        articleId: String,
        commentId: String,
    ) {
        coroutineScope.launch {
            deleteCommentUseCase(
                articleId = articleId,
                commentId = commentId,
            ).onSuccess {
                commentsState.update { currentState ->
                    check(currentState is ArticleCommentsState.Success)
                    currentState.copy(
                        comments = currentState.comments.filter { it.id != commentId }
                    )
                }
            }.onFailure { error ->
                _action.send(ArticleDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }

    private fun shareArticle() {
        TODO()
    }

    private fun createComment(articleId: String) {
        val currentState = commentsState.value
        check(currentState is ArticleCommentsState.Success)
        coroutineScope.launch {
            createCommentUseCase(
                articleId = articleId,
                text = currentState.comment,
            ).onSuccess {
                reloadComments(articleId)
                commentsState.update {
                    currentState.copy(
                        comment = "",
                    )
                }
            }.onFailure { error ->
                _action.send(ArticleDetailAction.ShowSnackbar(error.toString()))
            }
        }
    }

    private fun likeArticle() {
        state.update { currentState ->
            check(currentState is ArticleDetailState.Success)
            currentState.copy(
                article = currentState.article.copy(
                    isLiked = true,
                )
            )
        }
    }

    private fun dislikeArticle() {
        state.update { currentState ->
            check(currentState is ArticleDetailState.Success)
            currentState.copy(
                article = currentState.article.copy(
                    isLiked = false,
                )
            )
        }
    }

    private fun loadArticleDetail(articleId: String) {
        state.update { ArticleDetailState.Loading }
        coroutineScope.launch {
            getArticleUseCase(articleId).onSuccess { article ->
                state.update {
                    ArticleDetailState.Success(
                        article = article.toUi(),
                    )
                }
            }.onFailure { error ->
                state.update {
                    ArticleDetailState.Error(
                        message = getString(Res.string.unknown_error),
                    )
                }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            ArticleDetailAction.ShowSnackbar(
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
            check(currentState is ArticleCommentsState.Success)
            currentState.copy(
                comment = comment,
            )
        }
    }

    private fun deleteArticle() {
        val currentState = state.value
        state.update { ArticleDetailState.Loading }
        coroutineScope.launch {
            deleteArticleUseCase(articleId).onSuccess {
                _action.send(
                    ArticleDetailAction.ShowSnackbar(
                        message = getString(Res.string.article_deleted_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            ArticleDetailAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}