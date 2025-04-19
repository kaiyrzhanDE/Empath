package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditSubPost
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewSubPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SubPostRequest(
    @SerialName("id") val id: String?,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("imgs") val imageUrls: List<String?>?,
)

/**
 * Mapping for creating new sub post
 */
internal fun NewSubPost.toData(): SubPostRequest {
    return SubPostRequest(
        id = null,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}

/**
 * Mapping for edit sub post
 */
internal fun EditSubPost.toData(): SubPostRequest {
    return SubPostRequest(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}