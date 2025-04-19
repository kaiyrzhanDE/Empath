package kaiyrzhan.de.empath.features.vacancies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.pagination.map
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.ResponseToVacancyRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.pagingSource.employment.ResponsesPagingSource
import kaiyrzhan.de.empath.features.vacancies.data.pagingSource.employment.VacanciesPagingSource
import kaiyrzhan.de.empath.features.vacancies.data.remote.EmploymentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository
import kotlinx.coroutines.flow.Flow

internal class EmploymentRepositoryImpl(
    private val api: EmploymentApi
) : EmploymentRepository {

    override suspend fun getVacancies(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        educations: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                VacanciesPagingSource(
                    api = api,
                    query = query,
                    salaryFrom = salaryFrom,
                    salaryTo = salaryFrom,
                    workFormats = workFormats,
                    workExperiences = workExperiences,
                    educations = educations,
                    excludeWords = excludeWords,
                    includeWords = includeWords,
                )
            }
        ).flow
    }

    override suspend fun getResponses(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        educations: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ResponsesPagingSource(
                    api = api,
                    query = query,
                    salaryFrom = salaryFrom,
                    salaryTo = salaryFrom,
                    workFormats = workFormats,
                    workExperiences = workExperiences,
                    excludeWords = excludeWords,
                    includeWords = includeWords,
                    educations = educations,
                )
            }
        ).flow
    }

    override suspend fun responseToVacancy(
        vacancyId: String,
        cvId: String
    ): RequestResult<Any> {
        return api.responseToVacancy(
            vacancyId = vacancyId,
            request = ResponseToVacancyRequest(
                cvId = cvId,
            ),
        )
    }

    override suspend fun getCvs(): RequestResult<ListResult<Cv>> {
        return api.getCvs()
            .toDomain { cvs ->
                cvs.map { cv -> cv.toDomain() }
            }
    }
}