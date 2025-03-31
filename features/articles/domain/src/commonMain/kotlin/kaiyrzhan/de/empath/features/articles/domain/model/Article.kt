package kaiyrzhan.de.empath.features.articles.domain.model

public data class Article(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrls: List<String>,
    val tags: List<Tag>,
    val subArticles: List<SubArticle>,
    val viewsCount: Int,
    val likesCount: Int,
    val dislikesCount: Int,
    val author: Author,
)