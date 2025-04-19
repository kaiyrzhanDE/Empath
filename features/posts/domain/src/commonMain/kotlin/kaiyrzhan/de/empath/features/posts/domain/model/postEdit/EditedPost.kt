package kaiyrzhan.de.empath.features.posts.domain.model.postEdit

import kaiyrzhan.de.empath.features.posts.domain.model.Author
import kaiyrzhan.de.empath.features.posts.domain.model.Tag

public class EditedPost(
    public val id: String,
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subPosts: List<EditSubPost>,
    public val viewsCount: Int,
    public val likesCount: Int,
    public val dislikesCount: Int,
    public val author: Author,
)