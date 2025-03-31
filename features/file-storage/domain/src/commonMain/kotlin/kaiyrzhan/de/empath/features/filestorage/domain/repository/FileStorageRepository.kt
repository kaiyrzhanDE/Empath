package kaiyrzhan.de.empath.features.filestorage.domain.repository

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.filestorage.domain.model.File

public interface FileStorageRepository {

    public suspend fun downloadFile(
        filePath: String,
        onReadFile: (ByteArray) -> Unit,
    )

    public suspend fun uploadFile(
        fileType: String,
        storageName: String,
        image: ByteArray,
        imageType: String,
    ): RequestResult<File>

}