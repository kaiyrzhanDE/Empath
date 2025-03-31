package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.NewSubArticle
import kaiyrzhan.de.empath.features.articles.domain.model.SubArticle
import kotlinx.serialization.SerialName

internal data class SubarticleRequest(
    @SerialName("id") val id: String?,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("imageUrls") val imageUrls: List<String?>?,
)

/**
 * Mapping for creating new sub article
 */
internal fun NewSubArticle.toData(): SubarticleRequest {
    return SubarticleRequest(
        id = null,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}

/**
 * Mapping for edit sub article
 */
internal fun SubArticle.toData(): SubarticleRequest {
    return SubarticleRequest(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}