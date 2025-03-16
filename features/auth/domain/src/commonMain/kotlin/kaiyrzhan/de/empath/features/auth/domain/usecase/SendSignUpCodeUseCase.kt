package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class SendSignUpCodeUseCase(
    private val repository: AuthRepository,
) {
    public suspend operator fun invoke(email: String): Result<Any>{
        return repository.sendSignUpCode(email)
            .toDomain()
    }
}

public sealed interface SendSignUpCodeUseCaseError : Result.Error{
    public data object TooManySignUpAttempts : SendSignUpCodeUseCaseError
    public data object InvalidEmail : SendSignUpCodeUseCaseError
    public data object EmailAlreadyRegistered : SendSignUpCodeUseCaseError
}

private fun <S> RequestResult<S>.toDomain(): Result<S> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.TooManyRequests -> SendSignUpCodeUseCaseError.TooManySignUpAttempts
                StatusCode.UnProcessableEntity -> SendSignUpCodeUseCaseError.InvalidEmail
                else -> Result.Error.UnknownRemoteError(payload)
            }
        }
    }
}