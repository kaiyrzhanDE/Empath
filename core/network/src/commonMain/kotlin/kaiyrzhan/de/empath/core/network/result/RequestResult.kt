package kaiyrzhan.de.empath.core.network.result

public sealed interface RequestResult<out S> {
    public class Success<S>(
        public val data: S,
    ) : RequestResult<S>

    public sealed class Failure<S> : RequestResult<S> {

        public data class Error(
            public val statusCode: StatusCode,
            public val payload: Any? = null,
        ) : RequestResult<Nothing> {

            override fun toString(): String {
                return payload.toString()
            }

        }

        public class Exception(
            public val throwable: Throwable,
        ) : RequestResult<Nothing> {

            override fun toString(): String {
                return throwable.message.orEmpty()
            }
        }

    }
}


