package kaiyrzhan.de.empath.core.network.result

public sealed class RequestResult<out S> {
    public class Success<S>(public val data: S) : RequestResult<S>()
    public data class Error(public val error: RequestError) : RequestResult<Nothing>()
    public class Exception(public val throwable: Throwable) : RequestResult<Nothing>()
}

public suspend fun <S : Any> RequestResult<S>.onSuccess(
    executable: suspend (S) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Success<S>) {
        executable(data)
    }
}

public suspend fun <S> RequestResult<S>.onError(
    executable: suspend (requestError: RequestError) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Error) {
        executable(error)
    }
}

public suspend fun <S> RequestResult<S>.onException(
    executable: suspend (e: Throwable) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Exception) {
        executable(throwable)
    }
}


public data class RequestError(
    val code: Int,
    val message: String,
)


public fun <S, D> RequestResult<S>.map(
    onSuccess: (S) -> D,
): RequestResult<D> = when (this) {
    is RequestResult.Success -> RequestResult.Success(onSuccess(this.data))
    is RequestResult.Error -> RequestResult.Error(error)
    is RequestResult.Exception -> this
}


