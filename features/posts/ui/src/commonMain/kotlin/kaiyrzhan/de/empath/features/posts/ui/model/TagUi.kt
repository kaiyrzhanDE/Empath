package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.features.posts.domain.model.Tag
import kotlinx.serialization.Serializable

@Serializable
internal data class TagUi(
    val id: String? = null,
    val name: String,
) {
    companion object {
        fun create(name: String): TagUi {
            return TagUi(
                name = name
            )
        }
    }
}

internal fun Tag.toUi(): TagUi {
    return TagUi(
        id = id,
        name = name,
    )
}

internal fun TagUi.toDomain(): Tag {
    return Tag(
        id = id,
        name = name,
    )
}
