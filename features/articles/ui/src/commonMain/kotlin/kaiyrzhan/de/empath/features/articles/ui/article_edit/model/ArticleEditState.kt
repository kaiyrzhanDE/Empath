package kaiyrzhan.de.empath.features.articles.ui.article_edit.model

import kaiyrzhan.de.empath.features.articles.ui.model.article_edit.EditedArticleUi

internal sealed class ArticleEditState {
    object Initial : ArticleEditState()
    object Loading : ArticleEditState()
    class Error(val message: String) : ArticleEditState()
    data class Success(
        val editableArticle: EditedArticleUi,
        val originalArticle: EditedArticleUi,
    ) : ArticleEditState()

    companion object {
        fun default(): ArticleEditState {
            return Initial
        }
    }
}