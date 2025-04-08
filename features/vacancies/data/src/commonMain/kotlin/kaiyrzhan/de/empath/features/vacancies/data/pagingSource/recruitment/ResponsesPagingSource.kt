package kaiyrzhan.de.empath.features.vacancies.data.pagingSource.recruitment

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.remote.RecruitmentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import okio.IOException
import kotlin.collections.orEmpty

internal class ResponsesPagingSource(
    private val api: RecruitmentApi,
    private val vacancyId: String? = null,
) : PagingSource<Int, Response>() {

    override fun getRefreshKey(state: PagingState<Int, Response>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Response> {
        return try {
            val currentPage = params.key ?: 1
            val request = api.getResponses(
                page = currentPage,
                vacancyId = vacancyId,
                pageLimit = params.loadSize,
            )
            when (request) {
                is RequestResult.Success -> {
                    LoadResult.Page(
                        data = request.data.data
                            .orEmpty()
                            .filterNotNull()
                            .map { vacancy -> vacancy.toDomain() },
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