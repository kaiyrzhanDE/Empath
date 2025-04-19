package kaiyrzhan.de.empath.features.vacancies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.job.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.ChangeResponseStatusRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.CreateRecruiterRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.toData
import kaiyrzhan.de.empath.features.vacancies.data.pagingSource.recruitment.ResponsesPagingSource
import kaiyrzhan.de.empath.features.vacancies.data.pagingSource.recruitment.VacanciesPagingSource
import kaiyrzhan.de.empath.features.vacancies.data.remote.RecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Author
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import kotlinx.coroutines.flow.Flow

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
                    excludeWords = excludeWords,
                    includeWords = includeWords,
                    educations = educations,
                )
            }
        ).flow
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

    override suspend fun getResponses(
        vacancyId: String?,
    ): Flow<PagingData<Response>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ResponsesPagingSource(
                    api = api,
                    vacancyId = vacancyId,
                )
            }
        ).flow
    }

    override suspend fun changeResponseStatus(
        cvId: String,
        status: String,
        vacancyId: String
    ): RequestResult<Any> {
        return api.changeResponseStatus(
            request = ChangeResponseStatusRequest(
                cvId = cvId,
                status = status,
                vacancyId = vacancyId,
            ),
        )
    }

    override suspend fun getRecruiter(): RequestResult<Author> {
        return api.getRecruiter()
            .toDomain { author ->
                author.toDomain()
            }
    }
}