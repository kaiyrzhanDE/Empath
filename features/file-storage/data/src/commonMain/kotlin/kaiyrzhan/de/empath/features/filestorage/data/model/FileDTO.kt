package kaiyrzhan.de.empath.features.filestorage.data.model

import kaiyrzhan.de.empath.features.filestorage.domain.model.File
import kotlinx.serialization.SerialName
import kotlin.jvm.JvmInline

@JvmInline
internal value class FileDTO(
    @SerialName("url") val url: String,
)

internal fun FileDTO.toDomain(): File {
    return File(
        url = url,
    )
}