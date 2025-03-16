package kaiyrzhan.de.empath.features.auth.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.auth.data.remote.AuthApi
import kaiyrzhan.de.empath.features.auth.data.remote.createAuthApi
import kaiyrzhan.de.empath.features.auth.data.repository.AuthRepositoryImpl
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val authDataModule: Module = module() {
    single<AuthApi> { get<Ktorfit>().createAuthApi() }

    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
        )
    }
}