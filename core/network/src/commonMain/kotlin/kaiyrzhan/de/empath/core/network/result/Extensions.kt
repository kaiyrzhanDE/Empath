package kaiyrzhan.de.empath.core.network.result

import io.ktor.client.statement.HttpResponse

public fun HttpResponse.getStatusCode(): StatusCode =
    StatusCode.entries.find { it.code == status.value } ?: StatusCode.Unknown

public suspend fun <S : Any> RequestResult<S>.onSuccess(
    executable: suspend (S) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Success) {
        executable(data)
    }
}

public suspend fun <S> RequestResult<S>.onFailure(
    executable: suspend (failure: RequestResult.Failure<S>) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Failure) {
        executable(this)
    }
}

public suspend fun <S> RequestResult<S>.onError(
    executable: suspend (statusCode: StatusCode, payload: Any?) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Failure.Error) {
        executable(statusCode, payload)
    }
}

public suspend fun <S> RequestResult<S>.onError(
    executable: suspend (statusCode: StatusCode) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Failure.Error) {
        executable(statusCode)
    }
}

public suspend fun <S> RequestResult<S>.onException(
    executable: suspend (e: Throwable) -> Unit
): RequestResult<S> = apply {
    if (this is RequestResult.Failure.Exception) {
        executable(throwable)
    }
}

public fun <S, D> RequestResult<S>.map(
    onSuccess: (S) -> D,
): RequestResult<D> = when (this) {
    is RequestResult.Success -> RequestResult.Success(onSuccess(data))
    is RequestResult.Failure.Error -> RequestResult.Failure.Error(statusCode, payload)
    is RequestResult.Failure.Exception -> RequestResult.Failure.Exception(throwable)
}
