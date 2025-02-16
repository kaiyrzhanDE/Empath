package kaiyrzhan.de.empath.core.network

import de.jensklingenberg.ktorfit.http.GET

internal interface AuthApi {
    @GET("people/1/")
    suspend fun getPerson(): String
}