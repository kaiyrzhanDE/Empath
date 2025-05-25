package kaiyrzhan.de.empath.features.filestorage.domain.model

public enum class FileType(
    private val type: String,
) {
    IMAGE("imgs"),
    FILES("files");

    override fun toString(): String {
        return type
    }
}