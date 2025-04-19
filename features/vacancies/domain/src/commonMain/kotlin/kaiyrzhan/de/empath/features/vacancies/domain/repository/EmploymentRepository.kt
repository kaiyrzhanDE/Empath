package kaiyrzhan.de.empath.features.vacancies.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
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

    public suspend fun getCvs(): RequestResult<ListResult<Cv>>

}