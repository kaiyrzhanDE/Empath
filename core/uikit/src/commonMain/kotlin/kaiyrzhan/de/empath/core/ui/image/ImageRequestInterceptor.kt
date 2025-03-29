package kaiyrzhan.de.empath.core.ui.image

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.intercept.Interceptor
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageResult
import coil3.util.DebugLogger
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private class ImageRequestInterceptor : Interceptor, KoinComponent {
    val tokenProvider: TokenProvider = get()

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val bearerTokens = tokenProvider.getLocalToken().first()
        val originalRequest = chain.request

        val networkHeaders = NetworkHeaders.Builder()
            .add("Authorization", "Bearer ${bearerTokens.accessToken}")
            .build()
        val newRequest = originalRequest.newBuilder()
            .httpHeaders(networkHeaders)
            .build()
        return chain
            .withRequest(newRequest)
            .proceed()
    }
}

public fun createAuthorizedImageLoader(platformContext: PlatformContext): ImageLoader {
    return ImageLoader.Builder(platformContext)
        .components {
            add(ImageRequestInterceptor())
        }
        .logger(DebugLogger())
        .build()
}