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
    }
    return httpClient
}

