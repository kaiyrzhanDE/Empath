package kaiyrzhan.de.empath.features.posts.ui.model.postCreate

import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewSubPost
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.toDomain
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class NewSubPostUi(
    val id: Uuid = Uuid.random(),
    val title: String,
    val description: String,
    val images: List<ImageUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() || description.isNotBlank() || images.isNotEmpty()
    }

    companion object {
        fun create(): NewSubPostUi {
            return NewSubPostUi(
                title = "",
                description = "",
                images = emptyList(),
            )
        }
    }
}

internal fun NewSubPostUi.toDomain(): NewSubPost {
    return NewSubPost(
        title = title,
        description = description,
        imageUrls = images.toDomain(),
    )
}


