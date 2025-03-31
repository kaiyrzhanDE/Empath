package kaiyrzhan.de.empath.features.articles.data.model

import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kotlinx.serialization.SerialName

internal data class ArticleDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("text") val description: String?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("imgs") val imageUrls: List<String>?,
    @SerialName("tags") val tags: List<TagDTO?>?,
    @SerialName("sub_articles") val subArticles: List<SubArticleDTO?>?,
    @SerialName("views_cnt") val viewsCount: Int?,
    @SerialName("likes_cnt") val likesCount: Int?,
    @SerialName("dislikes_cnt") val dislikesCount: Int?,
    @SerialName("author") val author: AuthorDTO,
)

internal fun ArticleDTO.toDomain(): Article {
    return Article(
        id = id,
        title = title.orEmpty(),
        description = description.orEmpty(),
        isVisible = isVisible != false,
        imageUrls = imageUrls.orEmpty(),
        tags = tags
            .orEmpty()
            .filterNotNull()
            .map { tag -> tag.toDomain() },
        subArticles = subArticles
            .orEmpty()
            .filterNotNull()
            .map { subArticle -> subArticle.toDomain() },
        viewsCount = viewsCount ?: 0,
        likesCount = likesCount ?: 0,
        dislikesCount = dislikesCount ?: 0,
        author = author.toDomain(),
    )
}