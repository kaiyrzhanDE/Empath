package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PostDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("imgs") val imageUrls: List<String>?,
    @SerialName("tags") val tags: List<TagDTO?>?,
    @SerialName("sub_articles") val subPosts: List<SubPostDTO?>?,
    @SerialName("views_cnt") val viewsCount: Int?,
    @SerialName("likes_cnt") val likesCount: Int?,
    @SerialName("dislikes_cnt") val dislikesCount: Int?,
    @SerialName("author") val author: AuthorDTO,
)

internal fun PostDTO.toDomain(): Post {
    return Post(
        id = id,
        title = title.orEmpty(),
        description = description.orEmpty(),
        isVisible = isVisible != false,
        imageUrls = imageUrls.orEmpty(),
        tags = tags
            .orEmpty()
            .filterNotNull()
            .map { tag -> tag.toDomain() },
        subPosts = this@toDomain.subPosts
            .orEmpty()
            .filterNotNull()
            .map { post -> post.toDomain() },
        viewsCount = viewsCount ?: 0,
        likesCount = likesCount ?: 0,
        dislikesCount = dislikesCount ?: 0,
        author = author.toDomain(),
    )
}