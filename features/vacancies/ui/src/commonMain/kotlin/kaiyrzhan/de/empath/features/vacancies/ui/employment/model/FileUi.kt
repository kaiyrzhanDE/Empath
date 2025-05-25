package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.removeBaseUrl
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class FileUi(
    val id: Uuid = Uuid.random(),
    val url: String? = null,
    val platformFile: PlatformFile? = null,
    val isLoading: Boolean = false,
) {
    companion object {
        fun create(
            platformFile: PlatformFile,
            isLoading: Boolean,
        ): FileUi {
            return FileUi(
                platformFile = platformFile,
                isLoading = isLoading,
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
internal fun List<String>.toUi(): List<FileUi> {
    return this.map { url ->
        FileUi(
            url = url.addBaseUrl(),
        )
    }
}

internal fun List<FileUi>.toDomain(): List<String> {
    return this.mapNotNull { url -> url.url.removeBaseUrl() }
}