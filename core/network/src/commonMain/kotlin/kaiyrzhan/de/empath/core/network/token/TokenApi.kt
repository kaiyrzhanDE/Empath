package kaiyrzhan.de.empath.core.network.token

import de.jensklingenberg.ktorfit.http.POST
import kaiyrzhan.de.empath.core.network.result.RequestResult

internal interface TokenApi {

    @POST("users/refresh")
    suspend fun refreshToken(body: TokenEntity): RequestResult<TokenEntity>

}
