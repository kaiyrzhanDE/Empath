package kaiyrzhan.de.empath.features.vacancies.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.vacancies.data.remote.JobApi
import kaiyrzhan.de.empath.features.vacancies.data.remote.RecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.data.remote.createJobApi
import kaiyrzhan.de.empath.features.vacancies.data.remote.createRecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.data.repository.JobRepositoryImpl
import kaiyrzhan.de.empath.features.vacancies.data.repository.RecruitmentRepositoryImpl
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val vacanciesDataModule: Module = module {
    single<RecruitmentApi> { get<Ktorfit>().createRecruitmentApi() }
    single<JobApi> { get<Ktorfit>().createJobApi() }

    single<RecruitmentRepository> {
        RecruitmentRepositoryImpl(
            api = get<RecruitmentApi>(),
        )
    }

    single<JobRepository> {
        JobRepositoryImpl(
            api = get<JobApi>()
        )
    }
}