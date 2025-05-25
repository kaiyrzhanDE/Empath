package kaiyrzhan.de.empath.features.vacancies.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.NewCv
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv as EmploymentCv
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyRecommendations
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv as JobCv
import kotlinx.coroutines.flow.Flow

public interface EmploymentRepository {

    public suspend fun getVacancies(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        educations: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>>

    public suspend fun getResponses(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        educations: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>>

    public suspend fun responseToVacancy(
        vacancyId: String,
        cvId: String,
    ): RequestResult<Any>

    public suspend fun getCvs(): RequestResult<ListResult<EmploymentCv>>

    public suspend fun createCv(
        cv: NewCv,
    ): RequestResult<Any>

    public suspend fun updateCv(
        cvId: String,
        cv: JobCv,
    ): RequestResult<Any>

    public suspend fun deleteCv(
        cvId: String,
    ): RequestResult<Any>
}