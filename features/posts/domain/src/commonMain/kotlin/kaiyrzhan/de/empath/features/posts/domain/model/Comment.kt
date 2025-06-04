package kaiyrzhan.de.empath.features.posts.domain.model

import kotlinx.datetime.Instant

public class Comment(
    public val id: String,
    public val postId: String,
    public val parentId: String,
    public val isVisible: Boolean,
    public val text: String,
    public val author: Author,
    public val likesCount: Int,
    public val dislikesCount: Int,
    public val reaction: String,
    public val dateOfCreation: Instant?,
)