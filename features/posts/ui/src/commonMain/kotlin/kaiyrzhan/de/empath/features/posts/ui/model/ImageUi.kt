package kaiyrzhan.de.empath.features.posts.ui.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.removeBaseUrl
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class ImageUi(
    val id: Uuid = Uuid.random(),
    val imageUrl: String? = null,
    val platformFile: PlatformFile? = null,
    val isLoading: Boolean = false,
) {
    companion object {
        fun create(
            platformFile: PlatformFile,
            isLoading: Boolean,
        ): ImageUi {
            return ImageUi(
                platformFile = platformFile,
                isLoading = isLoading,
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
internal fun List<String>.toUi(): List<ImageUi> {
    return this.map { imageUrl ->
        ImageUi(
            imageUrl = imageUrl.addBaseUrl(),
        )
    }
}

internal fun List<ImageUi>.toDomain(): List<String> {
    return this.mapNotNull { it.imageUrl.removeBaseUrl() }
}
