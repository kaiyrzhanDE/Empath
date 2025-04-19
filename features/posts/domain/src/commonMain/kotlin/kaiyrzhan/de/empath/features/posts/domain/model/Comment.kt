package kaiyrzhan.de.empath.features.posts.domain.model

public class Comment(
    public val id: String,
    public val postId: String,
    public val text: String,
    public val author: Author,
)