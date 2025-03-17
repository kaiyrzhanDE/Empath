package kaiyrzhan.de.empath.features.auth.domain.di

import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.features.auth.domain.repository.AuthRepository
import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.ResetPasswordUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SignUpUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.VerifyCodeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val authDomainModule: Module = module {
    factory {
        LogInUseCase(
            repository = get<AuthRepository>(),
            tokenProvider = get<TokenProvider>(),
        )
    }

    factory {
        SendResetPasswordCodeUseCase(
            repository = get<AuthRepository>(),
        )
    }

    factory {
        SendSignUpCodeUseCase(
            repository = get<AuthRepository>(),
        )
    }

    factory {
        VerifyCodeUseCase(
            repository = get<AuthRepository>(),
        )
    }

    factory {
        ResetPasswordUseCase(
            repository = get<AuthRepository>(),
        )
    }

    factory {
        SignUpUseCase(
            repository = get<AuthRepository>(),
            tokenProvider = get<TokenProvider>(),
        )
    }
}