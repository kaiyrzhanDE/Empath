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
        repeatedPassword: String,
        nickname: String,
    ): Result<Any> {
        if(password != repeatedPassword) throw SignUpUseCaseError.PasswordsDontMatch
        return repository.signUp(email, password, nickname)
            .onSuccess { token -> tokenProvider.saveToken(token) }
            .toDomain()
    }

}

public sealed class SignUpUseCaseError : Result.Error, Throwable() {
    public data object InvalidEmailOrPassword : SignUpUseCaseError()
    public data object PasswordsDontMatch : SignUpUseCaseError()
}

private fun <S> RequestResult<S>.toDomain(): Result<S> {
    return when (this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.UnknownError(throwable)
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> SignUpUseCaseError.InvalidEmailOrPassword
                else -> Result.Error.UnknownRemoteError(payload)
            }
        }
    }
}