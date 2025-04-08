package kaiyrzhan.de.empath.features.vacancies.domain.di

import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.CreateRecruiterUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.CreateVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.DeleteVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.EditVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.GetVacanciesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val vacanciesDomainModule: Module = module {
    factory {
        GetVacanciesUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
    factory {
        CreateRecruiterUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
    factory {
        EditVacancyUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
    factory {
        DeleteVacancyUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
    factory {
        CreateVacancyUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
}