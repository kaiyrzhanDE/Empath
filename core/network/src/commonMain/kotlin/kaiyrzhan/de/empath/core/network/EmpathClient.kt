package kaiyrzhan.de.empath.core.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import kaiyrzhan.de.empath.core.network.token.TokenApi
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerializationException

internal const val BASE_URL = "http://80.90.191.162/"

internal fun empathClient(
    tokenProvider: TokenProvider,
    logger: BaseLogger,
): HttpClient {
    val httpClient = defaultHttpClient(
        logger = logger,
    ) {
        install(Auth) {
            bearer {
                loadTokens {
                    tokenProvider
                        .getLocalToken()
                        .first()
                        .run {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            )
                        }
                }
                refreshTokens {
                    tokenProvider
                        .refreshToken()
                        .run {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            )
                        }
                }
            }
        }
    }
    return httpClient
}

