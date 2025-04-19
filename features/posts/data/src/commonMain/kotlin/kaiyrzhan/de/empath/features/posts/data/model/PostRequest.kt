package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PostRequest(
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("imgs") val imageUrls: List<String>?,
    @SerialName("tags") val tags: List<TagRequest?>?,
    @SerialName("sub_articles") val subPosts: List<SubPostRequest?>?,
)

/**
 * Mapping for create new post
 */
internal fun NewPost.toData(): PostRequest {
    return PostRequest(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls,
        tags = tags.map { tag -> tag.toData() },
        subPosts = subPosts.map { subPost -> subPost.toData() },
    )
}

/**
 * Mapping for edit post
 */
internal fun EditedPost.toData(): PostRequest {
    return PostRequest(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls,
        tags = tags.map { tag -> tag.toData() },
        subPosts = subPosts.map { subPost -> subPost.toData() },
    )
}