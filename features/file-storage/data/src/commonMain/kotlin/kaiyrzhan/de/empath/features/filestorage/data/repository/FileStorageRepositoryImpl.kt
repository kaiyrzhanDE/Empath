package kaiyrzhan.de.empath.features.filestorage.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.utils.DEFAULT_HTTP_BUFFER_SIZE
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.filestorage.data.model.toDomain
import kaiyrzhan.de.empath.features.filestorage.data.remote.FileStorageApi
import kaiyrzhan.de.empath.features.filestorage.domain.model.File
import kaiyrzhan.de.empath.features.filestorage.domain.repository.FileStorageRepository
import kotlinx.io.readByteArray

internal class FileStorageRepositoryImpl(
    private val api: FileStorageApi,
) : FileStorageRepository {

    override suspend fun downloadFile(
        filePath: String,
        onBytesRead: (ByteArray) -> Unit,
    ) {
        return api.downloadFile(
            filePath = filePath,
        ).execute { response ->
            val channel: ByteReadChannel = response.bodyAsChannel()
            while (channel.isClosedForRead.not()) {
                val packet = channel.readRemaining(DEFAULT_HTTP_BUFFER_SIZE.toLong())
                while (packet.exhausted().not()) {
                    val bytes = packet.readByteArray()
                    onBytesRead(bytes)
                }
            }
        }
    }

    override suspend fun uploadFile(
        fileType: String,
        storageName: String,
        image: ByteArray,
        imageType: String,
    ): RequestResult<File> {
        return api.uploadFile(
            fileType = fileType,
            storageName = storageName,
            file = MultiPartFormDataContent(
                formData {
                    append(
                        key = "file",
                        value = image,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/$imageType")
                            append(
                                HttpHeaders.ContentDisposition,
                                "form-data; filename=\"image.$imageType\""
                            )
                        }
                    )
                }
            ),
        ).toDomain { file ->
            file.toDomain()
        }
    }

}

