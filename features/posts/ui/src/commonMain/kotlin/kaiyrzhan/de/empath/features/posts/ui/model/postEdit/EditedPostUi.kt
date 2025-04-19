package kaiyrzhan.de.empath.features.posts.ui.model.postEdit

import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.model.AuthorUi
import kaiyrzhan.de.empath.features.posts.ui.model.toDomain
import kaiyrzhan.de.empath.features.posts.ui.model.toUi

internal data class EditedPostUi(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val images: List<ImageUi>,
    val tags: List<TagUi>,
    val subPosts: List<EditedSubPostUi>,
    val viewsCount: Int,
    val likesCount: Int,
    val isViewed: Boolean = false,
    val isLiked: Boolean = false,
    val dislikesCount: Int,
    val author: AuthorUi,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() ||
                description.isNotBlank() ||
                images.isNotEmpty() ||
                tags.isNotEmpty() ||
                subPosts.any { subPost -> subPost.isChanged() }
    }
}

internal fun Post.toEdit(): EditedPostUi {
    return EditedPostUi(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        images = imageUrls.toUi(),
        tags = tags.map { tag -> tag.toUi() },
        subPosts = subPosts.map { subPost -> subPost.toEdit() },
        author = author.toUi(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

internal fun EditedPostUi.toDomain(): EditedPost {
    return EditedPost(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = images.toDomain(),
        id = id,
        tags = tags.map { tag -> tag.toDomain() },
        subPosts = subPosts.map { subPost -> subPost.toDomain() },
        viewsCount = viewsCount,
        likesCount = likesCount,
        dislikesCount = dislikesCount,
        author = author.toDomain(),
    )
}
