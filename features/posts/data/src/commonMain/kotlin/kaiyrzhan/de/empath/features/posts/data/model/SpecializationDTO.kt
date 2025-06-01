package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.features.posts.domain.model.Specialization
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SpecializationDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String?,
)

internal fun SpecializationDTO.toDomain(): Specialization {
    return Specialization(
        id = id,
        name = name.orEmpty(),
    )
}