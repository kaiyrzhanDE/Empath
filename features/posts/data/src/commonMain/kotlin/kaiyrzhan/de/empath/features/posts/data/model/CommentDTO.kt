package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CommentDTO(
    @SerialName("id") val id: String,
    @SerialName("article_id") val postId: String,
    @SerialName("text") val text: String?,
    @SerialName("author") val author: AuthorDTO,
)

internal fun CommentDTO.toDomain(): Comment {
    return Comment(
        id = id,
        postId = postId,
        text = text.orEmpty(),
        author = author.toDomain(),
    )
}