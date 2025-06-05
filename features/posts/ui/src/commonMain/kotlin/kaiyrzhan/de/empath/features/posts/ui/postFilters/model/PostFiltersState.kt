package kaiyrzhan.de.empath.features.posts.ui.postFilters.model

import kaiyrzhan.de.empath.features.posts.ui.model.PostFiltersUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.model.SpecializationUi

internal data class PostFiltersState(
    val reactionTypes: List<PostReactionType>,
    val reactionType: PostReactionType,
    val query: String,
    val excludeWords: String,
    val includeWords: String,
    val specializationQuery: String,
    val selectedSpecializations: List<SpecializationUi>,
) {
    companion object {
        fun default(postFilters: PostFiltersUi): PostFiltersState {
            return PostFiltersState(
                reactionTypes = listOf(
                    PostReactionType.LIKED,
                    PostReactionType.VIEWED,
                    PostReactionType.DISLIKED,
                ),
                query = postFilters.query,
                includeWords = postFilters.includeWords,
                excludeWords = postFilters.excludeWords,
                specializationQuery = "",
                reactionType = postFilters.postReactionType,
                selectedSpecializations = postFilters.selectedSpecializations,
            )
        }
    }
}