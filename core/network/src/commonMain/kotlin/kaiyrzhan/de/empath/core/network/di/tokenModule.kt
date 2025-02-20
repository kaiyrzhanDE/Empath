package kaiyrzhan.de.empath.core.network.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.core.network.EmpathClient
import kaiyrzhan.de.empath.core.network.defaultHttpClient
import kaiyrzhan.de.empath.core.network.token.TokenApi
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.network.token.TokenProviderImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val TOKEN_MODULE = "tokenModule"

public val tokenModule: Module = module {
    val tokenModuleQualifier = named(TOKEN_MODULE)
    single(tokenModuleQualifier) { defaultHttpClient() }
    single(tokenModuleQualifier) {
        val ktorfit: Ktorfit = get(tokenModuleQualifier)
        ktorfit.create<TokenApi>()
    }
    single<TokenProvider>(tokenModuleQualifier) {
        TokenProviderImpl(
            preferences = get(),
            tokenApi = get(tokenModuleQualifier),
            logger = get(),
        )
    }
    single{
        EmpathClient(
            tokenProvider = get(tokenModuleQualifier)
        )
    }
}
