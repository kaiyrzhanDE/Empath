package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedArticle
import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ArticleRequest(
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("imgs") val imageUrls: List<String>?,
    @SerialName("tags") val tags: List<TagRequest?>?,
    @SerialName("sub_articles") val subArticles: List<SubarticleRequest?>?,
)

/**
 * Mapping for create new article
 */
internal fun NewArticle.toData(): ArticleRequest {
    return ArticleRequest(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls,
        tags = tags.map { tag -> tag.toData() },
        subArticles = subArticles.map { subArticle -> subArticle.toData() },
    )
}

/**
 * Mapping for edit article
 */
internal fun EditedArticle.toData(): ArticleRequest {
    return ArticleRequest(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls,
        tags = tags.map { tag -> tag.toData() },
        subArticles = subArticles.map { subArticle -> subArticle.toData() },
    )
}