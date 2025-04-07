package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedSubArticle
import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewSubArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SubarticleRequest(
    @SerialName("id") val id: String?,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("imgs") val imageUrls: List<String?>?,
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
internal fun EditedSubArticle.toData(): SubarticleRequest {
    return SubarticleRequest(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}