package kaiyrzhan.de.empath.features.posts.domain.model.postCreate

import kaiyrzhan.de.empath.features.posts.domain.model.Tag

public class NewPost(
    public val title: String,
    public val description: String,
    public val isVisible: Boolean,
    public val imageUrls: List<String>,
    public val tags: List<Tag>,
    public val subPosts: List<NewSubPost>,
)