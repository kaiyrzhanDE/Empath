package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.removeBaseUrl
import kaiyrzhan.de.empath.features.posts.domain.model.SubPost
import kotlin.uuid.ExperimentalUuidApi

internal data class SubPostUi(
    val id: String,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
)

@OptIn(ExperimentalUuidApi::class)
internal fun SubPost.toUi(): SubPostUi {
    return SubPostUi(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls.mapNotNull { url -> url.addBaseUrl() },
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun SubPostUi.toDomain(): SubPost {
    return SubPost(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls.mapNotNull { url -> url.removeBaseUrl() },
    )
}
