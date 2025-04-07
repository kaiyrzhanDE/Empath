package kaiyrzhan.de.empath.features.articles.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.articles.data.model.toDomain
import kaiyrzhan.de.empath.features.articles.data.remote.ArticlesApi
import kaiyrzhan.de.empath.features.articles.domain.model.Tag
import okio.IOException
import kotlin.collections.orEmpty

internal class TagsPagingSource(
    private val api: ArticlesApi,
    private val query: String?,
) : PagingSource<Int, Tag>() {

    override fun getRefreshKey(state: PagingState<Int, Tag>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tag> {
        return try {
            val currentPage = params.key ?: 1
            val request = api.getTags(
                page = currentPage,
                query = query,
            )
            when (request) {
                is RequestResult.Success -> {
                    LoadResult.Page(
                        data = request.data.data
                            .orEmpty()
                            .filterNotNull()
                            .map { tag -> tag.toDomain() },
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