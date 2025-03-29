package kaiyrzhan.de.empath.core.utils.result

public sealed interface Result<out S> {
    public data class Success<S>(
        public val data: S,
    ) : Result<S>

    public interface Error : Result<Nothing> {

        public data class DefaultError(
            public val message: String,
        ) : Error {
            override fun toString(): String {
                return message
            }
        }

    }
}