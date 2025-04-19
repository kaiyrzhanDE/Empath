package kaiyrzhan.de.empath.features.posts.ui.model.postEdit

import kaiyrzhan.de.empath.features.posts.domain.model.SubPost
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditSubPost
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.toDomain
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class EditedSubPostUi(
    val id: Uuid = Uuid.random(),
    val subPostId: String?,
    val title: String,
    val description: String,
    val images: List<ImageUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() || description.isNotBlank() || images.isNotEmpty()
    }

    companion object {
        fun create(): EditedSubPostUi {
            return EditedSubPostUi(
                subPostId = null,
                title = "",
                description = "",
                images = emptyList(),
            )
        }
    }
}

internal fun EditedSubPostUi.toDomain(): EditSubPost {
    return EditSubPost(
        id = subPostId,
        title = title,
        description = description,
        imageUrls = images.toDomain(),
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun SubPost.toEdit(): EditedSubPostUi {
    return EditedSubPostUi(
        subPostId = id,
        title = title,
        description = description,
        images = imageUrls.toUi(),
    )
}

