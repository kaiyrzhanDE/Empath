package kaiyrzhan.de.empath.features.filestorage.domain.model

public enum class StorageName(
    private val type: String,
) {
    ARTICLE("article");

    override fun toString(): String {
        return type
    }
}