package kaiyrzhan.de.empath.features.filestorage.domain.model

public enum class StorageName(
    private val type: String,
) {
    POST("article"),
    CV("cv");

    override fun toString(): String {
        return type
    }
}