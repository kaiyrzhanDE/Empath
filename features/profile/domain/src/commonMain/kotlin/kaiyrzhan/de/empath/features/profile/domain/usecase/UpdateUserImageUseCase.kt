package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode

public class UpdateUserImageUseCase(
    private val repository: ProfileRepository,
) {
    public suspend operator fun invoke(
        image: ByteArray,
        imageName: String,
        imageType: String,
    ): Result<Any> {
        return repository
            .updateUserImage(
                image = image,
                imageName = imageName,
                imageType = imageType,
            )
            .toResult()
    }
}

public sealed class UpdateUserImageUseCaseError : Result.Error {
    public data object UserImageTooLarge : UpdateUserImageUseCaseError()
}

public fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> when (result.statusCode) {
            StatusCode.PayloadTooLarge -> UpdateUserImageUseCaseError.UserImageTooLarge
            else -> Result.Error.DefaultError(result.toString())
        }
    }
}