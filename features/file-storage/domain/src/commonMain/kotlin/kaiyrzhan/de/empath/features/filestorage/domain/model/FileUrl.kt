package kaiyrzhan.de.empath.features.filestorage.domain.model

import kotlin.jvm.JvmInline

@JvmInline
public value class File(
    public val url: String,
){
    override fun toString(): String {
        return url
    }
}

