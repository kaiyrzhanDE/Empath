package kaiyrzhan.de.empath.core.network.di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import kaiyrzhan.de.empath.core.network.BASE_URL
import kaiyrzhan.de.empath.core.network.empathClient
import kaiyrzhan.de.empath.core.network.result.RequestResultConverterFactory
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import org.koin.core.module.Module
import org.koin.dsl.module

public val networkModule: Module = module {
    single {
        empathClient(
            logger = get<BaseLogger>(),
            tokenProvider = get<TokenProvider>(),
        )
    }
    single<Ktorfit> {
        ktorfit {
            baseUrl(BASE_URL)
            converterFactories(RequestResultConverterFactory.create())
            httpClient(get<HttpClient>())
        }
    }
}