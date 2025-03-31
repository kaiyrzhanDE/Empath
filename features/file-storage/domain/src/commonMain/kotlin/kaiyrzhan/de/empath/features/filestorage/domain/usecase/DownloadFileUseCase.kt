package kaiyrzhan.de.empath.features.filestorage.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.filestorage.domain.repository.FileStorageRepository

public class DownloadFileUseCase(
    private val repository: FileStorageRepository,
) {
    public suspend operator fun invoke(
        filePath: String,
        onReadFile: (ByteArray) -> Unit,
    ) {
        repository.downloadFile(
            filePath = filePath,
            onReadFile = onReadFile,
        )
    }
}

public sealed class DownloadFileUseCaseError : Result.Error {
    public data object FileNotFoundError : DownloadFileUseCaseError()
}

private fun RequestResult<ByteArray>.toResult(): Result<ByteArray> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> when (result.statusCode) {
            StatusCode.NotFound -> DownloadFileUseCaseError.FileNotFoundError
            else -> Result.Error.DefaultError(result.toString())
        }
    }
}