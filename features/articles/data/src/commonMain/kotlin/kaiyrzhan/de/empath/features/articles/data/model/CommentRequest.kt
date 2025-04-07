package kaiyrzhan.de.empath.features.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CommentRequest(
    @SerialName("text") val text: String,
)