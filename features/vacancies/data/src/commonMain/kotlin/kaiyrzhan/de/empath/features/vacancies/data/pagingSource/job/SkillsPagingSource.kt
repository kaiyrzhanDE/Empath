package kaiyrzhan.de.empath.features.vacancies.data.pagingSource.job

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.remote.JobApi
import kaiyrzhan.de.empath.features.vacancies.data.remote.RecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import okio.IOException
import kotlin.collections.orEmpty

internal class SkillsPagingSource(
    private val api: JobApi,
    private val query: String?,
) : PagingSource<Int, Skill>() {

    override fun getRefreshKey(state: PagingState<Int, Skill>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Skill> {
        return try {
            val currentPage = params.key ?: 1
            val request = api.getSkills(
                page = currentPage,
                pageLimit = params.loadSize,
                query = query,
            )
            when (request) {
                is RequestResult.Success -> {
                    LoadResult.Page(
                        data = request.data.data
                            .orEmpty()
                            .filterNotNull()
                            .map { skill -> skill.toDomain() },
                        prevKey = request.data.previousPage,
                        nextKey = request.data.nextPage,
                    )
                }

                is RequestResult.Failure -> {
                    return when (request) {
                        is RequestResult.Failure.Error -> {
                            LoadResult.Error(Throwable(request.toString()))
                        }

                        is RequestResult.Failure.Exception -> {
                            LoadResult.Error(request.throwable)
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}