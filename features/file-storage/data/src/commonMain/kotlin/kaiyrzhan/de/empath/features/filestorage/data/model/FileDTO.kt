package kaiyrzhan.de.empath.features.filestorage.data.model

import kaiyrzhan.de.empath.features.filestorage.domain.model.File
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class FileDTO(
    @SerialName("url") val url: String,
)

internal fun FileDTO.toDomain(): File {
    return File(
        url = url,
    )
}