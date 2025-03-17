package kaiyrzhan.de.empath.features.auth.data.repository

import kaiyrzhan.de.empath.core.network.token.Token
import kaiyrzhan.de.empath.core.network.token.toDomain
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.map
import kaiyrzhan.de.empath.features.auth.data.model.ForgetPasswordRequest
import kaiyrzhan.de.empath.features.auth.data.model.LoginRequest
import kaiyrzhan.de.empath.features.auth.data.model.SignUpRequest
import kaiyrzhan.de.empath.features.auth.data.model.VerifyCodeRequest
import kaiyrzhan.de.empath.features.auth.data.remote.AuthApi
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    internal val api: AuthApi,
) : AuthRepository {

    override suspend fun logIn(
        email: String,
        password: String
    ): RequestResult<Token> {
        return api.logIn(body = LoginRequest(email, password))
            .map { token -> token.toDomain() }
    }

    override suspend fun sendSignUpCode(email: String): RequestResult<Any> {
        return api.sendSignUpOtp(email = email)
    }

    override suspend fun sendResetPasswordCode(email: String): RequestResult<Any> {
        return api.sendResetPasswordOtp(email = email)
    }

    override suspend fun verifyCode(email: String, code: String): RequestResult<Any> {
        return api.verifyCode(body = VerifyCodeRequest(email, code))
    }

    override suspend fun resetPassword(email: String, password: String): RequestResult<Any> {
        return api.resetPassword(body = ForgetPasswordRequest(email, password))
    }

    override suspend fun signUp(
        email: String,
        password: String,
        nickname: String
    ): RequestResult<Token> {
        return api.signUp(body = SignUpRequest(email, password, nickname))
            .map { token -> token.toDomain() }
    }
}