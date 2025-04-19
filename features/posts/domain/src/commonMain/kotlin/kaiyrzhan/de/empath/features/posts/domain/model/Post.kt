package kaiyrzhan.de.empath.features.posts.domain.model

public class Post(
    public val id: String,
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subPosts: List<SubPost>,
    public val viewsCount: Int,
    public val likesCount: Int,
    public val dislikesCount: Int,
    public val author: Author,
)