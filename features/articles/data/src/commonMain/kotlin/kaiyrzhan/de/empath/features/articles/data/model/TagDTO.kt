package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.Tag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TagDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String?,
)

internal fun TagDTO.toDomain(): Tag {
    return Tag(
        id = id,
        name = name.orEmpty(),
    )
}