package kaiyrzhan.de.empath.features.articles.ui.model

import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.removeBaseUrl
import kaiyrzhan.de.empath.features.articles.domain.model.Article

internal data class ArticleUi(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrls: List<String>,
    val tags: List<TagUi>,
    val subArticles: List<SubArticleUi>,
    val viewsCount: Int,
    val likesCount: Int,
    val isViewed: Boolean = false,
    val isLiked: Boolean = false,
    val dislikesCount: Int,
    val author: AuthorUi,
)

internal fun Article.toUi(): ArticleUi {
    return ArticleUi(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls.mapNotNull { url -> url.addBaseUrl() },
        tags = tags.map { tag -> tag.toUi() },
        subArticles = subArticles.map { it.toUi() },
        author = author.toUi(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

internal fun ArticleUi.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls.mapNotNull { url -> url.removeBaseUrl() },
        tags = tags.map { tag -> tag.toDomain() },
        subArticles = subArticles.map { subArticle -> subArticle.toDomain() },
        author = author.toDomain(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

