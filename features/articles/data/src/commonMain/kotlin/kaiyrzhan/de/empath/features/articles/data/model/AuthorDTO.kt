package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AuthorDTO(
    @SerialName("id") val id: String,
    @SerialName("nickname") val nickname: String?,
    @SerialName("img") val imageUrl: String?,
    @SerialName("full_name") val fullName: String?,
)

internal fun AuthorDTO.toDomain(): Author {
    return Author(
        id = id,
        nickname = nickname.orEmpty(),
        imageUrl = imageUrl,
        fullName = fullName.orEmpty(),
    )
}
