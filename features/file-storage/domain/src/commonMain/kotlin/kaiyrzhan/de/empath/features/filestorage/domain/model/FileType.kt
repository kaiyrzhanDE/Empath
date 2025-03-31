package kaiyrzhan.de.empath.features.filestorage.domain.model

public enum class FileType(
    private val type: String,
) {
    IMAGE("imgs");

    override fun toString(): String {
        return type
    }
}