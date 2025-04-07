package kaiyrzhan.de.empath.features.articles.ui.article_detail.model

internal sealed interface ArticleDetailEvent {
    data object ArticleLikeClick : ArticleDetailEvent
    data object ArticleDislikeClick : ArticleDetailEvent
    data object EditArticle : ArticleDetailEvent
    data object DeleteArticle : ArticleDetailEvent
    data object ReloadArticle : ArticleDetailEvent
    data object BackClick : ArticleDetailEvent
    data object ArticleShare : ArticleDetailEvent

    data class CommentDelete(val commentId: String) : ArticleDetailEvent
    data class CommentEditMode(val commentId: String) : ArticleDetailEvent
    data object CommentEdit : ArticleDetailEvent
    data object CommentCreate : ArticleDetailEvent
    data object ReloadComments : ArticleDetailEvent
    data class CommentChange(val comment: String) : ArticleDetailEvent
}