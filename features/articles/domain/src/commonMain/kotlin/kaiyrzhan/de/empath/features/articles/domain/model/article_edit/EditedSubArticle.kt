package kaiyrzhan.de.empath.features.articles.domain.model.article_edit

public data class EditedSubArticle(
    val id: String?,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
)