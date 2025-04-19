package kaiyrzhan.de.empath.features.posts.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CommentRequest(
    @SerialName("text") val text: String,
)