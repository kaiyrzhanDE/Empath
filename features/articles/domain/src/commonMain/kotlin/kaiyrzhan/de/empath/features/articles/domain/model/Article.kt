package kaiyrzhan.de.empath.features.articles.domain.model

public class Article(
    public val id: String,
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subArticles: List<SubArticle>,
    public val viewsCount: Int,
    public val likesCount: Int,
    public val dislikesCount: Int,
    public val author: Author,
)