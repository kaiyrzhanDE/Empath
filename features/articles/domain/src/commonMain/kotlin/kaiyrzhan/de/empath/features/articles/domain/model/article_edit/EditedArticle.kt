package kaiyrzhan.de.empath.features.articles.domain.model.article_edit

import kaiyrzhan.de.empath.features.articles.domain.model.Author
import kaiyrzhan.de.empath.features.articles.domain.model.Tag

public data class EditedArticle(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrls: List<String>,
    val tags: List<Tag>,
    val subArticles: List<EditedSubArticle>,
    val viewsCount: Int,
    val likesCount: Int,
    val dislikesCount: Int,
    val author: Author,
)