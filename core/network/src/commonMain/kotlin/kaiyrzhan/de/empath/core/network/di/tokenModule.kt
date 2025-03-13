package kaiyrzhan.de.empath.core.network.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import kaiyrzhan.de.empath.core.network.BASE_URL
import kaiyrzhan.de.empath.core.network.defaultHttpClient
import kaiyrzhan.de.empath.core.network.result.RequestResultConverterFactory
import kaiyrzhan.de.empath.core.network.token.TokenApi
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.network.token.TokenProviderImpl
import kaiyrzhan.de.empath.core.network.token.createTokenApi
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val TOKEN_MODULE = "tokenModule"

public val tokenModule: Module = module(createdAtStart = true) {
    single<HttpClient>(named(TOKEN_MODULE)) {
        defaultHttpClient(logger = get<BaseLogger>())
    }
    single<Ktorfit>(named(TOKEN_MODULE)) {
        ktorfit {
            baseUrl(BASE_URL)
            converterFactories(RequestResultConverterFactory.create())
            httpClient(get<HttpClient>(named(TOKEN_MODULE)))
        }
    }
    single<TokenApi>(named(TOKEN_MODULE)) {
        val tokenApi: TokenApi = get<Ktorfit>(named(TOKEN_MODULE)).createTokenApi()
        tokenApi
    }
    single<TokenProvider> {
        TokenProviderImpl(
            preferences = get<DataStore<Preferences>>(),
            logger = get<BaseLogger>(),
            tokenApi = get<TokenApi>(named(TOKEN_MODULE)),
        )
    }
}
