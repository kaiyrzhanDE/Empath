package kaiyrzhan.de.empath.features.posts.data.model

import kaiyrzhan.de.empath.core.utils.logger.ifNull
import kaiyrzhan.de.empath.features.posts.domain.model.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AuthorDTO(
    @SerialName("id") val id: String,
    @SerialName("nickname") val nickname: String?,
    @SerialName("img") val imageUrl: String?,
    @SerialName("full_name") val fullName: String?,
    @SerialName("rating") val rating: Int?,
)

internal fun AuthorDTO.toDomain(): Author {
    return Author(
        id = id,
        nickname = nickname.orEmpty(),
        imageUrl = imageUrl,
        fullName = fullName.orEmpty(),
        rating = rating.ifNull { 0 },
    )
}
