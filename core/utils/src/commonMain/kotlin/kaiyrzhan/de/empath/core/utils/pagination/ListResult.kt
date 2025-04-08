package kaiyrzhan.de.empath.core.utils.pagination

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public data class ListResult<R>(
    val count: Int,
    val currentPage: Int,
    val nextPage: Int?,
    val previousPage: Int?,
    val data: List<R>,
)

@Serializable
public data class ListResultDTO<T>(
    @SerialName("count") val count: Int?,
    @SerialName("page") val currentPage: Int?,
    @SerialName("next") val nextPage: Int?,
    @SerialName("prev") val previousPage: Int?,
    @SerialName("results") val data: List<T?>?,
)

public inline fun <T, R> ListResultDTO<T>.map(
    mapper: (T) -> R,
): ListResult<R> {
    return ListResult(
        count = count ?: -1,
        currentPage = currentPage ?: -1,
        nextPage = nextPage,
        previousPage = previousPage,
        data = data
            ?.filterNotNull()
            ?.map(mapper)
            .orEmpty(),
    )
}

