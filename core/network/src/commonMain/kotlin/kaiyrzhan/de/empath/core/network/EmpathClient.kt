package kaiyrzhan.de.empath.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient

internal class EmpathClient internal constructor(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {
    private val ktorfit by lazy {
        Ktorfit.Builder()
            .httpClient(httpClient)
            .baseUrl(baseUrl)
            .build()
    }

    private val api by lazy { ktorfit.create<AuthApi>() }

}

