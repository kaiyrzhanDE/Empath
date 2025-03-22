package kaiyrzhan.de.empath.features.profile.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.profile.data.remote.ProfileApi
import kaiyrzhan.de.empath.features.profile.data.remote.createProfileApi
import kaiyrzhan.de.empath.features.profile.data.repository.ProfileRepositoryImpl
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val profileDataModule: Module = module {
    single<ProfileApi> { get<Ktorfit>().createProfileApi() }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            api = get<ProfileApi>(),
        )
    }
}