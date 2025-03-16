package kaiyrzhan.de.empath.core.utils.result

public sealed interface Result<out S> {
    public data class Success<S>(
        public val data: S,
    ) : Result<S>

    public interface Error : Result<Nothing> {
        public data class UnknownError(
            public val throwable: Throwable
        ) : Error

        public data class UnknownRemoteError(
            public val payload: Any? = null,
        ) : Error{
            override fun toString(): String {
                return payload.toString()
            }
        }
    }
}