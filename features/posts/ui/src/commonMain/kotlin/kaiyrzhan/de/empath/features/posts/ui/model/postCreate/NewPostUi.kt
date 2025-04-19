package kaiyrzhan.de.empath.features.posts.ui.model.postCreate

import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.model.toDomain

internal data class NewPostUi(
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val images: List<ImageUi>,
    val tags: List<TagUi>,
    val subPosts: List<NewSubPostUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() &&
                description.isNotBlank() &&
                tags.isNotEmpty()
    }

    companion object {
        fun default(): NewPostUi {
            return NewPostUi(
                title = "",
                description = "",
                isVisible = true,
                images = emptyList(),
                tags = emptyList(),
                subPosts = listOf(NewSubPostUi.create()),
            )
        }
    }
}

internal fun NewPostUi.toDomain(): NewPost {
    return NewPost(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = images.toDomain(),
        tags = tags.map { tag -> tag.toDomain() },
        subPosts = subPosts.map { subPost -> subPost.toDomain() },
    )
}
