package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.SubPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SubPostDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("imageUrls") val imageUrls: List<String?>?,
)

internal fun SubPostDTO.toDomain(): SubPost {
    return SubPost(
        id = id,
        title = title.orEmpty(),
        description = description.orEmpty(),
        imageUrls = imageUrls
            .orEmpty()
            .filterNotNull(),
    )
}