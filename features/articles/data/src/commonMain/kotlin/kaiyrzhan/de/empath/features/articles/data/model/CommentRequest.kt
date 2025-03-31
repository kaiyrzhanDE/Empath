package kaiyrzhan.de.empath.features.articles.data.model

import kotlinx.serialization.SerialName

internal data class CommentRequest(
    @SerialName("text") val text: String,
)