package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.core.utils.result.Result

public class GetUserUseCase(
    private val repository: ProfileRepository,
) {
    public suspend operator fun invoke(): Result<User> {
        return repository
            .getUser()
            .toDomain()
    }
}

public sealed interface GetUserUseCaseError : Result.Error

private fun RequestResult<User>.toDomain(): Result<User> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> Result.Error.UnknownRemoteError(payload)
    }
}

