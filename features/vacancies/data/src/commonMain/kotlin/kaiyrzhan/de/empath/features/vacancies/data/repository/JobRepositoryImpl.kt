package kaiyrzhan.de.empath.features.vacancies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.job.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.pagingSource.job.SkillsPagingSource
import kaiyrzhan.de.empath.features.vacancies.data.remote.JobApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow

internal class JobRepositoryImpl(
    private val api: JobApi,
) : JobRepository {

    override suspend fun getVacancyDetail(id: String): RequestResult<VacancyDetail> {
        return api.getVacancyDetail(id = id)
            .toDomain { detail -> detail.toDomain() }
    }

    override suspend fun getSkills(
        query: String?,
    ): Flow<PagingData<Skill>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                SkillsPagingSource(
                    api = api,
                    query = query,
                )
            }
        ).flow
    }

    override suspend fun getEmploymentTypes(): RequestResult<List<Skill>> {
        return api.getEmploymentTypes()
            .toDomain { skills ->
                skills.map { skill -> skill.toDomain() }
            }
    }

    override suspend fun getWorkFormats(): RequestResult<List<Skill>> {
        return api.getWorkFormats()
            .toDomain { skills ->
                skills.map { skill -> skill.toDomain() }
            }
    }

    override suspend fun getWorkSchedules(): RequestResult<List<Skill>> {
        return api.getWorkSchedules()
            .toDomain { skills ->
                skills.map { skill -> skill.toDomain() }
            }
    }
}