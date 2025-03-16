package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class VerifyCodeUseCase(
    private val repository: AuthRepository,
) {
    public suspend operator fun invoke(email: String, code: String): Result<Any> {
        return repository
            .verifyCode(email, code)
            .toDomain()
    }
}

public sealed interface VerifyCodeUseCaseError : Result.Error {
    public data object InvalidCode : VerifyCodeUseCaseError
}

private fun <S> RequestResult<S>.toDomain(): Result<S> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.BadRequest -> VerifyCodeUseCaseError.InvalidCode
                StatusCode.UnProcessableEntity -> VerifyCodeUseCaseError.InvalidCode
                else -> Result.Error.UnknownRemoteError(payload)
            }
        }
    }
}