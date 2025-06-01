package kaiyrzhan.de.empath.features.posts.ui.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PostFiltersUi(
    val query: String = "",
    val includeWords: String = "",
    val excludeWords: String = "",
    val selectedSpecializations: List<SpecializationUi> = emptyList(),
    val postReactionType: PostReactionType = PostReactionType.NONE,
)