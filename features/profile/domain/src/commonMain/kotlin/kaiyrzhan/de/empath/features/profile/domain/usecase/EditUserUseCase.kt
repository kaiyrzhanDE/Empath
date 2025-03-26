package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository

public class EditUserUseCase(
    private val repository: ProfileRepository,
) {
    public suspend operator fun invoke(user: User): Result<Any> {
        return repository
            .editUser(user)
            .toDomain()
    }
}

public sealed interface EditUserUseCaseError : Result.Error

private fun RequestResult<Any>.toDomain(): Result<Any> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> Result.Error.UnknownRemoteError(payload)
    }
}