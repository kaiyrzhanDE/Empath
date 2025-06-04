package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kotlinx.datetime.LocalDateTime

internal data class CommentUi(
    val id: String,
    val parentId: String,
    val postId: String,
    val isVisible: Boolean,
    val text: String,
    val author: AuthorUi,
    val likesCount: Int,
    val dislikesCount: Int,
    val reaction: Reaction,
    val dateOfCreation: LocalDateTime?,
)

internal fun Comment.toUi(): CommentUi {
    return CommentUi(
        id = id,
        parentId = parentId,
        postId = postId,
        isVisible = isVisible,
        text = text,
        author = author.toUi(),
        likesCount = likesCount,
        dislikesCount = dislikesCount,
        reaction = reaction.toEnumSafe(default = Reaction.DEFAULT) { enum, value ->
            enum.type.equals(other = value, ignoreCase = true)
        },
        dateOfCreation = dateOfCreation.toLocalDateTime(),
    )
}