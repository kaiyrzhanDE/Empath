package kaiyrzhan.de.empath.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kaiyrzhan.de.empath.core.network.token.TokenApi
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

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

    @Suppress("DEPRECATION")
    private val tokenApi by lazy { ktorfit.create<TokenApi>() }

}

internal fun EmpathClient(
    baseUrl: String = "BASE_URL",
    json: Json = DefaultJson,
    tokenProvider: TokenProvider,
): EmpathClient {
    val httpClient = defaultHttpClient {
        install(Auth) {
            bearer {
                loadTokens {
                    tokenProvider
                        .getLocalToken()
                        .first()
                        .let { token ->
                            BearerTokens(accessToken = token.accessToken, refreshToken = token.refreshToken)
                        }
                }
                refreshTokens {
                    tokenProvider
                        .refreshToken()
                        .let { token ->
                            BearerTokens(
                                accessToken = token.accessToken,
                                refreshToken = token.refreshToken,
                            )
                        }
                }
            }
        }
        install(ContentNegotiation) {
            json(json)
        }
    }
    return EmpathClient(baseUrl, httpClient)
}

