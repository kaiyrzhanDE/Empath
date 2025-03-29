package kaiyrzhan.de.empath.core.network.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kaiyrzhan.de.empath.core.utils.datastore.DataStoreKeys
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


public interface TokenProvider {
    public suspend fun getLocalToken(): Flow<Token>

    public suspend fun getCurrentToken(): Token?

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
        val token = preferences.data.map { dataStore ->
            Token(
                accessToken = dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN].orEmpty(),
                refreshToken = dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN].orEmpty(),
            )
        }
        return token
    }

    override suspend fun getCurrentToken(): Token? {
        val currentToken = getLocalToken().firstOrNull()
        return currentToken
    }

    override suspend fun deleteLocalToken(): Boolean {
        return try {
            logger.d(this.className(), "deleteLocalToken")
            preferences.edit { dataStore ->
                dataStore.remove(DataStoreKeys.USER_AUTH_ACCESS_TOKEN)
                dataStore.remove(DataStoreKeys.USER_AUTH_REFRESH_TOKEN)
            }
            true
        } catch (exception: Exception) {
            logger.d(this.className(), "Error occured while deleting token: ${exception.message}")
            false
        }
    }

    override suspend fun saveToken(token: Token) {
        logger.d(this.className(), "saveToken $token")
        preferences.edit { dataStore ->
            dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN] = token.accessToken
            dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun refreshToken(): Token {
        val currentToken = getCurrentToken()?.toData() ?: throw RefreshTokenException("Invalid refresh token")
        return when (val request = tokenApi.refreshToken(currentToken)) {
            is RequestResult.Success -> {
                logger.d(this.className(), "refreshToken successfully: ${request.data}")
                val token = request.data.toDomain()
                saveToken(token)
                token
            }

            is RequestResult.Failure.Error -> {
                logger.d(this.className(), "refreshToken error: ${request.statusCode} - ${request.payload}")
                throw RefreshTokenException("Invalid refresh token")
            }

            is RequestResult.Failure.Exception -> {
                logger.d(this.className(), "refreshToken exception: ${request.throwable.message}")
                throw RefreshTokenException("Invalid refresh token")
            }
        }
    }

}
