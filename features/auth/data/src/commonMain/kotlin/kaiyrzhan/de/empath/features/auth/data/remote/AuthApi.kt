package kaiyrzhan.de.empath.features.auth.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.features.auth.data.model.ForgetPasswordRequest
import kaiyrzhan.de.empath.features.auth.data.model.LoginRequest
import kaiyrzhan.de.empath.features.auth.data.model.ResetPasswordRequest
import kaiyrzhan.de.empath.features.auth.data.model.SignUpRequest
import kaiyrzhan.de.empath.features.auth.data.model.VerifyCodeRequest

internal interface AuthApi {

    @POST("/api/{version}/auth/signup")
    suspend fun signUp(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body body: SignUpRequest,
    )

    @POST("/api/{version}/auth/login")
    suspend fun logIn(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body body: LoginRequest,
    )

    @POST("/api/{version}/auth/logout")
    suspend fun logOut(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
    )

    @POST("/api/{version}/auth/password")
    suspend fun resetPassword(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body body: ResetPasswordRequest,
    )

    @POST("/api/{version}/auth/forget-password")
    suspend fun forgetPassword(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body body: ForgetPasswordRequest,
    )

    @POST("/api/{version}/auth/reset-otp")
    suspend fun sendResetOtp(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("email") email: String,
    )

    @POST("/api/{version}/auth/signup-otp")
    suspend fun sendSignUpOtp(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("email") email: String,
    )

    @POST("/api/{version}/auth/verify-code")
    suspend fun verifyCode(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body body: VerifyCodeRequest,
    )
}