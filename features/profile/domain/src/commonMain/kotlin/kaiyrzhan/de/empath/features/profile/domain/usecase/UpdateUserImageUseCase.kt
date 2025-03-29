package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.core.utils.result.Result

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
            .toDomain()
    }
}

public sealed interface UpdateUserImageUseCaseError : Result.Error

private fun RequestResult<Any>.toDomain(): Result<Any> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> Result.Error.UnknownRemoteError(payload)
    }
}

