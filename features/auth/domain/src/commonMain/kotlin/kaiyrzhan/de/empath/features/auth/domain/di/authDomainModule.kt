package kaiyrzhan.de.empath.features.auth.domain.di

import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.VerifyCodeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val authDomainModule: Module = module {
    factory {
        LogInUseCase(
            repository = get(),
            tokenProvider = get(),
        )
    }

    factory {
        SendResetPasswordCodeUseCase(
            repository = get(),
        )
    }

    factory {
        SendSignUpCodeUseCase(
            repository = get(),
        )
    }

    factory {
        VerifyCodeUseCase(
            repository = get(),
        )
    }
}