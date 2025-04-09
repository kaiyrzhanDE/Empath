package kaiyrzhan.de.empath.features.vacancies.data.pagingSource.employment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.remote.EmploymentApi
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import okio.IOException

internal class ResponsesPagingSource(
    private val api: EmploymentApi,
    private val query: String?,
    private val salaryFrom: Int?,
    private val salaryTo: Int?,
    private val workExperiences: List<String>,
    private val workFormats: List<String>,
    private val educations: List<String>,
    private val excludeWords: List<String>,
    private val includeWords: List<String>,
) : PagingSource<Int, Vacancy>() {

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val currentPage = params.key ?: 1
            val request = api.getResponses(
                page = currentPage,
                query = query,
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                workExperiences = workExperiences,
                workFormats = workFormats,
                educations = educations,
                excludeWords = excludeWords,
                includeWords = includeWords,
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