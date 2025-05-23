package kaiyrzhan.de.empath.features.auth.domain.usecase

import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.utils.result.*
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

public class LogInUseCase(
    private val repository: AuthRepository,
    private val tokenProvider: TokenProvider,
) {
    public suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<Any> {
        return repository
            .logIn(email, password)
            .onSuccess { token -> tokenProvider.saveToken(token) }
            .toResult()
    }
}

public sealed interface LogInUseCaseError : Result.Error {
    public data object TooManyLoginAttempts : LogInUseCaseError
    public data object InvalidEmailOrPassword : LogInUseCaseError
}

private fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> LogInUseCaseError.InvalidEmailOrPassword
                StatusCode.TooManyRequests -> LogInUseCaseError.TooManyLoginAttempts
                else -> Result.Error.DefaultError(result.toString())
            }
        }
    }
}