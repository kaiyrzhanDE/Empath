package kaiyrzhan.de.empath.features.articles.ui.articles.model


internal sealed interface ArticlesEvent {
    data class ArticleClick(val articleId: String) : ArticlesEvent
    data object ArticleCreateClick : ArticlesEvent
    data class ArticleDelete(val articleId: String) : ArticlesEvent
    data class ArticleEdit(val articleId: String) : ArticlesEvent
    data class ArticleSearch(val query: String) : ArticlesEvent
    data class ArticleLike(val articleId: String) : ArticlesEvent
    data class ArticleDislike(val articleId: String) : ArticlesEvent
    data class ArticleView(val articleId: String) : ArticlesEvent
    data class ArticleShare(val articleId: String) : ArticlesEvent
}