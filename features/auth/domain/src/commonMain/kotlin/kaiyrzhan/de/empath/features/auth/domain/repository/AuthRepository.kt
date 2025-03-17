package kaiyrzhan.de.empath.features.auth.domain.repository

import kaiyrzhan.de.empath.core.network.token.Token
import kaiyrzhan.de.empath.core.utils.result.RequestResult

public interface AuthRepository {

    public suspend fun logIn(email: String, password: String): RequestResult<Token>

    public suspend fun sendSignUpCode(email: String): RequestResult<Any>

    public suspend fun sendResetPasswordCode(email: String): RequestResult<Any>

    public suspend fun verifyCode(email: String, code: String): RequestResult<Any>

    public suspend fun resetPassword(email: String, password: String): RequestResult<Any>

}