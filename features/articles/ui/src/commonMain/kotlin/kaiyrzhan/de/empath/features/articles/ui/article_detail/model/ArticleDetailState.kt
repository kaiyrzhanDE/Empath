package kaiyrzhan.de.empath.features.articles.ui.article_detail.model

import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.CommentUi

internal sealed class ArticleDetailState {
    object Initial : ArticleDetailState()
    object Loading : ArticleDetailState()
    class Error(val message: String) : ArticleDetailState()
    data class Success(
        val article: ArticleUi,
    ) : ArticleDetailState()

    companion object {
        fun default(): ArticleDetailState {
            return Initial
        }
    }
}

internal sealed class ArticleCommentsState {
    object Initial : ArticleCommentsState()
    object Loading : ArticleCommentsState()
    class Error(val message: String) : ArticleCommentsState()
    data class Success(
        val comments: List<CommentUi> = emptyList(),
        val comment: String = "",
        val commentMode: CommentMode = CommentMode.Create,
    ) : ArticleCommentsState()

    companion object {
        fun default(): ArticleCommentsState {
            return Initial
        }
    }
}