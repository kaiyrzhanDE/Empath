package kaiyrzhan.de.empath.features.articles.ui.articleCreate.model

import kaiyrzhan.de.empath.features.articles.ui.model.article_create.NewArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.UserUi

internal sealed class ArticleCreateState {
    object Initial : ArticleCreateState()
    object Loading : ArticleCreateState()
    class Error(val message: String) : ArticleCreateState()
    data class Success(
        val user: UserUi,
        val newArticle: NewArticleUi,
    ) : ArticleCreateState()

    companion object {
        fun default(): ArticleCreateState {
            return Initial
        }
    }
}