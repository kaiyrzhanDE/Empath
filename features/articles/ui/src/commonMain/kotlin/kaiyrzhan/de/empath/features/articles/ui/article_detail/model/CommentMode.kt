package kaiyrzhan.de.empath.features.articles.ui.article_detail.model

internal sealed interface CommentMode {
    data object Loading : CommentMode
    data class Edit(val commentId: String) : CommentMode
    data object Create : CommentMode
}