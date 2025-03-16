package kaiyrzhan.de.empath.core.utils.result

public suspend fun <S : Any> Result<S>.onSuccess(
    executable: suspend (S) -> Unit
): Result<S> {
    return apply {
        if (this is Result.Success) {
            executable(data)
        }
    }
}

public suspend fun <S> Result<S>.onFailure(
    executable: suspend (error: Result.Error) -> Unit
): Result<S> {
    return apply {
        if (this is Result.Error) {
            executable(this)
        }
    }
}
