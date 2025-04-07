package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CommentDTO(
    @SerialName("id") val id: String,
    @SerialName("article_id") val articleId: String,
    @SerialName("text") val text: String?,
    @SerialName("author") val author: AuthorDTO,
)

internal fun CommentDTO.toDomain(): Comment {
    return Comment(
        id = id,
        articleId = articleId,
        text = text.orEmpty(),
        author = author.toDomain(),
    )
}