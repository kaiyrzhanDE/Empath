package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.removeBaseUrl
import kaiyrzhan.de.empath.features.posts.domain.model.Post

internal data class PostUi(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrls: List<String>,
    val tags: List<TagUi>,
    val subPosts: List<SubPostUi>,
    val viewsCount: Int,
    val likesCount: Int,
    val isViewed: Boolean = false,
    val isLiked: Boolean = false,
    val dislikesCount: Int,
    val author: AuthorUi,
)

internal fun Post.toUi(): PostUi {
    return PostUi(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls.mapNotNull { url -> url.addBaseUrl() },
        tags = tags.map { tag -> tag.toUi() },
        subPosts = subPosts.map { subPost -> subPost.toUi() },
        author = author.toUi(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

internal fun PostUi.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = imageUrls.mapNotNull { url -> url.removeBaseUrl() },
        tags = tags.map { tag -> tag.toDomain() },
        subPosts = subPosts.map { subPost -> subPost.toDomain() },
        author = author.toDomain(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

