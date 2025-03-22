package kaiyrzhan.de.empath.features.profile.data.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.data.model.UserDTO

internal interface ProfileApi {

    @GET("/api/{version}/users/me")
    suspend fun getUser(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
    ): RequestResult<UserDTO>

}