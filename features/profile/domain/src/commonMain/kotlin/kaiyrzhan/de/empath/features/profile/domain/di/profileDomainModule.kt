package kaiyrzhan.de.empath.features.profile.domain.di

import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val profileDomainModule: Module = module {
    factory {
        GetUserUseCase(
            repository = get<ProfileRepository>(),
        )
    }
}