package kaiyrzhan.de.empath.core.utils.result

public sealed interface RequestResult<out S> {

    public class Success<S>(
        public val data: S,
    ) : RequestResult<S>

    public sealed interface Failure : RequestResult<Nothing> {

        public data class Error(
            public val statusCode: StatusCode,
            public val payload: Any? = null,
        ) : Failure {

            override fun toString(): String {
                return payload.toString()
            }
        }

        public class Exception(
            public val throwable: Throwable,
        ) : Failure {

            override fun toString(): String {
                return throwable.message.orEmpty()
            }
        }
    }
}


