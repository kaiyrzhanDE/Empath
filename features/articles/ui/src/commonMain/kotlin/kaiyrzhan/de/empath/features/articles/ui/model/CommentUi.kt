package kaiyrzhan.de.empath.features.articles.ui.model

import kaiyrzhan.de.empath.features.articles.domain.model.Comment

internal data class CommentUi(
    val id: String,
    val articleId: String,
    val text: String,
    val author: AuthorUi,
)

internal fun Comment.toUi(): CommentUi {
    return CommentUi(
        id = id,
        articleId = articleId,
        text = text,
        author = author.toUi(),
    )
}