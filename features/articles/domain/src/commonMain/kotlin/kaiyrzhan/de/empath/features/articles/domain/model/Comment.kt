package kaiyrzhan.de.empath.features.articles.domain.model

public data class Comment(
    val id: String,
    val articleId: String,
    val text: String,
    val author: Author,
)