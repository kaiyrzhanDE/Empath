package kaiyrzhan.de.empath.core.network.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kaiyrzhan.de.empath.core.network.result.RequestResult
import kaiyrzhan.de.empath.core.network.result.StatusCode
import kaiyrzhan.de.empath.core.network.result.onSuccess
import kaiyrzhan.de.empath.core.utils.datastore.DataStoreKeys
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TOKEN_PROVIDER = "TokenProvider"

internal interface TokenProvider {
    public suspend fun getLocalToken(): Flow<Token>

    public suspend fun deleteLocalToken(): Boolean

    public suspend fun saveToken(token: Token)

    public suspend fun refreshToken(): Token

}

internal class TokenProviderImpl(
    private val tokenApi: TokenApi,
    private val preferences: DataStore<Preferences>,
    private val logger: BaseLogger,
) : TokenProvider {

    override suspend fun getLocalToken(): Flow<Token> {
        logger.d(TOKEN_PROVIDER, "getLocalToken")
        return preferences.data.map { dataStore ->
            Token(
                accessToken = dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN].orEmpty(),
                refreshToken = dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN].orEmpty(),
            )
        }
    }

    override suspend fun deleteLocalToken(): Boolean {
        logger.d(TOKEN_PROVIDER, "deleteLocalToken")
        return try {
            preferences.edit { dataStore ->
                dataStore.remove(DataStoreKeys.USER_AUTH_ACCESS_TOKEN)
                dataStore.remove(DataStoreKeys.USER_AUTH_REFRESH_TOKEN)
            }
            true
        } catch (exception: Exception) {
            logger.d(TOKEN_PROVIDER, "Error occured while deleting token: ${exception.message}")
            false
        }
    }

    override suspend fun saveToken(token: Token) {
        logger.d(TOKEN_PROVIDER, "saveToken $token")
        preferences.edit { dataStore ->
            dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN] = token.accessToken
            dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun refreshToken(): Token {
        val currentToken = getLocalToken().first().toData()
        val request = tokenApi.refreshToken(currentToken)
        return when (request) {
            is RequestResult.Success -> {
                logger.d(TOKEN_PROVIDER, "refreshToken successfully: ${request.data}")
                request.data.toDomain()
            }

            is RequestResult.Failure.Error -> {
                logger.d(TOKEN_PROVIDER, "refreshToken error: ${request.statusCode} - ${request.payload}")

                if (request.statusCode == StatusCode.Unauthorized) {
                    deleteLocalToken()
                    throw AuthenticationException("Invalid refresh token")
                }

                throw RequestException("Token refresh failed: ${request.statusCode}")
            }

            is RequestResult.Failure.Exception -> {
                logger.d(TOKEN_PROVIDER, "refreshToken exception: ${request.throwable.message}")
                throw request.throwable
            }
        }
    }

}
