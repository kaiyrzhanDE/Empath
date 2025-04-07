package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.SubArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SubArticleDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("imageUrls") val imageUrls: List<String?>?,
)

internal fun SubArticleDTO.toDomain(): SubArticle {
    return SubArticle(
        id = id,
        title = title.orEmpty(),
        description = description.orEmpty(),
        imageUrls = imageUrls
            .orEmpty()
            .filterNotNull(),
    )
}