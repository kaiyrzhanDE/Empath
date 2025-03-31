package kaiyrzhan.de.empath.features.filestorage.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.filestorage.domain.model.File
import kaiyrzhan.de.empath.features.filestorage.domain.model.FileType
import kaiyrzhan.de.empath.features.filestorage.domain.model.StorageName
import kaiyrzhan.de.empath.features.filestorage.domain.repository.FileStorageRepository

public class UploadFileUseCase(
    private val repository: FileStorageRepository,
) {
    public suspend operator fun invoke(
        fileType: FileType,
        storageName: StorageName,
        image: ByteArray,
        imageType: String,
    ): Result<File> {
        return repository
            .uploadFile(
                fileType = fileType.toString(),
                storageName = storageName.toString(),
                image = image,
                imageType = imageType,
            )
            .toResult()
    }
}

public sealed class UploadFileUseCaseError : Result.Error {
    public data object FileTooLargeError : UploadFileUseCaseError()
}

private fun RequestResult<File>.toResult(): Result<File> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> when (result.statusCode) {
            StatusCode.PayloadTooLarge -> UploadFileUseCaseError.FileTooLargeError
            else -> Result.Error.DefaultError(result.toString())
        }
    }
}