package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.features.posts.domain.model.Comment

internal data class CommentUi(
    val id: String,
    val postId: String,
    val text: String,
    val author: AuthorUi,
)

internal fun Comment.toUi(): CommentUi {
    return CommentUi(
        id = id,
        postId = postId,
        text = text,
        author = author.toUi(),
    )
}