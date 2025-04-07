package kaiyrzhan.de.empath.features.articles.domain.model.article_create

import kaiyrzhan.de.empath.features.articles.domain.model.Tag

public data class NewArticle(
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrls: List<String>,
    val tags: List<Tag>,
    val subArticles: List<NewSubArticle>,
)