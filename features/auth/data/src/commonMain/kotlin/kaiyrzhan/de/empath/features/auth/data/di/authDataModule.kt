package kaiyrzhan.de.empath.features.auth.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.auth.data.remote.AuthApi
import org.koin.core.module.Module
import org.koin.dsl.module
//import kaiyrzhan.de.empath.features.auth.data.remote.createAuthApi

public val authDataModule: Module = module(){
    single<AuthApi> {
//        get<Ktorfit>().createAuthApi()
        get<Ktorfit>().create<AuthApi>()
    }
}