package kaiyrzhan.de.empath.features.articles.domain.model.article_create

import kaiyrzhan.de.empath.features.articles.domain.model.Tag

public class NewArticle(
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subArticles: List<NewSubArticle>,
)