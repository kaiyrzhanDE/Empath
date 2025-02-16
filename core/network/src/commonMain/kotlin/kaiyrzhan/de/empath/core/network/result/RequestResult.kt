package kaiyrzhan.de.empath.core.network.result

import kaiyrzhan.de.empath.core.network.result.model.RequestError

sealed interface RequestResult<out S, out E>

class Success<S>(val data: S) : RequestResult<S, Nothing>

data class Error<out E>(
    val requestError: RequestError<E>,
) : RequestResult<Nothing, E>

class Exception(val e: Throwable) : RequestResult<Nothing, Nothing>

suspend fun <S : Any, E> RequestResult<S, E>.onSuccess(
    executable: suspend (S) -> Unit
): RequestResult<S, E> = apply {
    if (this is Success<S>) {
        executable(data)
    }
}

suspend fun <S, E> RequestResult<S, E>.onError(
    executable: suspend (requestError: RequestError<E>) -> Unit
): RequestResult<S, E> = apply {
    if (this is Error<E>) {
        executable(requestError)
    }
}

suspend fun <S, E> RequestResult<S, E>.onException(
    executable: suspend (e: Throwable) -> Unit
): RequestResult<S, E> = apply {
    if (this is Exception) {
        executable(e)
    }
}

fun <D, P, S, T> RequestResult<D, S>.map(
    onSuccess: (D) -> P,
    onError: (S) -> T
): RequestResult<P, T> = when (this) {
    is Success -> Success(onSuccess(this.data))
    is Error -> Error(requestError.map(onError))
    is Exception -> this
}


fun <E, T> RequestError<E>.map(transform: (E) -> T): RequestError<T> {
    return RequestError(
        code = this.code,
        message = this.message,
        data = this.data?.let(transform)
    )
}
