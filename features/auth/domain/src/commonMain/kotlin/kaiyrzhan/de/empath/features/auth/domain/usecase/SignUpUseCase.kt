package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class SignUpUseCase(
    private val repository: AuthRepository,
    private val tokenProvider: TokenProvider,
) {
    public suspend operator fun invoke(
        email: String,
        password: String,
        nickname: String,
    ): Result<Any> {
        return repository.signUp(email, password, nickname)
            .onSuccess { token -> tokenProvider.saveToken(token) }
            .toResult()
    }

}

public sealed class SignUpUseCaseError : Result.Error {
    public data object InvalidEmailOrPassword : SignUpUseCaseError()
}

private fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> SignUpUseCaseError.InvalidEmailOrPassword
                else -> Result.Error.DefaultError(result.toString())
            }
        }
    }
}