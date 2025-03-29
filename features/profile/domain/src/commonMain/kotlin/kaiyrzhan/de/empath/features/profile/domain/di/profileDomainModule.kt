package kaiyrzhan.de.empath.features.profile.domain.di

import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.features.profile.domain.usecase.EditUserUseCase
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kaiyrzhan.de.empath.features.profile.domain.usecase.UpdateUserImageUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val profileDomainModule: Module = module {
    factory {
        GetUserUseCase(
            repository = get<ProfileRepository>(),
        )
    }
    factory {
        EditUserUseCase(
            repository = get<ProfileRepository>(),
        )
    }
    factory {
        UpdateUserImageUseCase(
            repository = get<ProfileRepository>(),
        )
    }
}