package kaiyrzhan.de.empath.core.network.token

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import kaiyrzhan.de.empath.core.network.result.RequestResult

internal interface TokenApi {

    @POST("users/refresh")
    public suspend fun refreshToken(@Body body: TokenEntity): RequestResult<TokenEntity>

}
