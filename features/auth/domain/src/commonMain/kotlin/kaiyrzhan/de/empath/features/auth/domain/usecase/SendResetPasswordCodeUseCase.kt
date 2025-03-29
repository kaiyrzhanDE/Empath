package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class SendResetPasswordCodeUseCase(
    private val repository: AuthRepository,
) {
    public suspend operator fun invoke(email: String): Result<Any>{
        return repository.sendResetPasswordCode(email)
            .toResult()
    }
}

public sealed interface SendResetPasswordCodeUseCaseError : Result.Error{
    public data object TooManyResetPasswordAttempts : SendResetPasswordCodeUseCaseError
    public data object InvalidEmail : SendResetPasswordCodeUseCaseError
    public data object EmailIsNotRegistered : SendResetPasswordCodeUseCaseError
}

private fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.TooManyRequests -> SendResetPasswordCodeUseCaseError.TooManyResetPasswordAttempts
                StatusCode.UnProcessableEntity -> SendResetPasswordCodeUseCaseError.InvalidEmail
                StatusCode.Conflict -> SendResetPasswordCodeUseCaseError.EmailIsNotRegistered
                else -> Result.Error.DefaultError(result.toString())
            }
        }
    }
}