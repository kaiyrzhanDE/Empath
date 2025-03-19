package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class ResetPasswordUseCase(
    private val repository: AuthRepository,
) {
    public suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<Any> {
        return repository
            .resetPassword(email, password)
            .toDomain()
    }
}

public sealed class ResetPasswordUseCaseError : Result.Error {
    public data object InvalidPasswordOrEmail : ResetPasswordUseCaseError()
}

private fun <S> RequestResult<S>.toDomain(): Result<S> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> ResetPasswordUseCaseError.InvalidPasswordOrEmail
                else -> Result.Error.UnknownRemoteError(payload)
            }
        }
    }
}