package kaiyrzhan.de.empath.features.vacancies.domain.di

import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetEmploymentTypesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetSkillsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetVacancyDetailUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkFormatsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkSchedulesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.ChangeResponseStatusUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.CreateRecruiterUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.CreateVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.DeleteVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.EditVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetResponsesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetVacanciesUseCase
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
    factory {
        GetResponsesUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }
    factory {
        ChangeResponseStatusUseCase(
            repository = get<RecruitmentRepository>(),
        )
    }

    factory {
        GetVacancyDetailUseCase(
            repository = get<JobRepository>(),
        )
    }
    factory {
        GetWorkFormatsUseCase(
            repository = get<JobRepository>(),
        )
    }
    factory {
        GetWorkSchedulesUseCase(
            repository = get<JobRepository>(),
        )
    }
    factory {
        GetEmploymentTypesUseCase(
            repository = get<JobRepository>(),
        )
    }
    factory {
        GetSkillsUseCase(
            repository = get<JobRepository>(),
        )
    }
}