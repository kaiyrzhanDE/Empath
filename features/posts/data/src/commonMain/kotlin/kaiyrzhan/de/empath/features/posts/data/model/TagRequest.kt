package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.Tag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class TagRequest(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String?,
)

internal fun Tag.toData(): TagRequest {
    return TagRequest(
        name = name,
        id = id,
    )
}


