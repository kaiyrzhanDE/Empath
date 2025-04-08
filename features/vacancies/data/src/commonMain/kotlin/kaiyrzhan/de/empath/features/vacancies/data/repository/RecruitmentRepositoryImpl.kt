package kaiyrzhan.de.empath.features.vacancies.data.repository

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.pagination.map
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.CreateRecruiterRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.toData
import kaiyrzhan.de.empath.features.vacancies.data.remote.RecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

internal class RecruitmentRepositoryImpl(
    private val api: RecruitmentApi,
) : RecruitmentRepository {

    override suspend fun createRecruiter(
        companyName: String,
        companyDescription: String,
        email: String,
    ): RequestResult<Any> {
        return api.createRecruiter(
            request = CreateRecruiterRequest(
                companyName = companyName,
                companyDescription = companyDescription,
                email = email,
            ),
        )
    }

    override suspend fun getVacancies(
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
        education: List<String>,
        query: String?,
    ): RequestResult<ListResult<Vacancy>> {
        return api.getVacancies(
            salaryFrom = salaryFrom,
            salaryTo = salaryTo,
            workExperiences = workExperiences,
            workFormats = workFormats,
            excludeWords = excludeWords,
            includeWords = includeWords,
            query = query,
            education = education,
        ).toDomain { vacancies ->
            vacancies.map { vacancy -> vacancy.toDomain() }
        }
    }

    override suspend fun createVacancy(vacancy: NewVacancy): RequestResult<Any> {
        return api.createVacancy(
            request = vacancy.toData(),
        )
    }

    override suspend fun deleteVacancy(id: String): RequestResult<Any> {
        return api.deleteVacancy(
            id = id,
        )
    }

    override suspend fun editVacancy(
        id: String,
        vacancy: NewVacancy
    ): RequestResult<Any> {
        return api.editVacancy(
            id = id,
            request = vacancy.toData(),
        )
    }

}