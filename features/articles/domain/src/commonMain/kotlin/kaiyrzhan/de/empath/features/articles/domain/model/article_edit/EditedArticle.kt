package kaiyrzhan.de.empath.features.articles.domain.model.article_edit

import kaiyrzhan.de.empath.features.articles.domain.model.Author
import kaiyrzhan.de.empath.features.articles.domain.model.Tag

public class EditedArticle(
    public val id: String,
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subArticles: List<EditedSubArticle>,
    public val viewsCount: Int,
    public val likesCount: Int,
    public val dislikesCount: Int,
    public val author: Author,
)