package kaiyrzhan.de.empath.features.articles.domain.model

public class Comment(
    public val id: String,
    public val articleId: String,
    public val text: String,
    public val author: Author,
)