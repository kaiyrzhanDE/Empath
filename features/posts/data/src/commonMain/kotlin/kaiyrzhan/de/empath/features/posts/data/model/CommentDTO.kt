package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.core.utils.toInstantOrNull
import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CommentDTO(
    @SerialName("id") val id: String,
    @SerialName("article_id") val postId: String,
    @SerialName("parent_id") val parentId: String?,
    @SerialName("text") val text: String?,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("likes_cnt") val likesCount: Int?,
    @SerialName("dislikes_cnt") val dislikesCount: Int?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("reaction_status") val reaction: String?,
    @SerialName("created_at") val dateOfCreation: String?,
)

internal fun CommentDTO.toDomain(): Comment {
    return Comment(
        id = id,
        postId = postId,
        parentId = parentId.orEmpty(),
        text = text.orEmpty(),
        author = author.toDomain(),
        likesCount = likesCount ?: 0,
        dislikesCount = dislikesCount ?: 0,
        isVisible = isVisible == true,
        reaction = reaction.orEmpty(),
        dateOfCreation = dateOfCreation?.toInstantOrNull(),
    )
}