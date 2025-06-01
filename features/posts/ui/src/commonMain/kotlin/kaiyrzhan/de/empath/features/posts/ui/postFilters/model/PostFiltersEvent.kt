package kaiyrzhan.de.empath.features.posts.ui.postFilters.model

import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.model.SpecializationUi

internal sealed interface PostFiltersEvent {
    data class ReactionTypeSelect(val reactionType: PostReactionType) : PostFiltersEvent
    data class IncludeWordsChange(val includeWords: String) : PostFiltersEvent
    data class ExcludeWordsChange(val excludeWords: String) : PostFiltersEvent
    data class QueryChange(val query: String) : PostFiltersEvent
    data class SpecializationQueryChange(val query: String) : PostFiltersEvent
    data object BackClick : PostFiltersEvent
    data object Clear : PostFiltersEvent
    data object Apply : PostFiltersEvent
    data class SpecializationSelect(val specialization: SpecializationUi) : PostFiltersEvent
    data class SpecializationRemove(val specialization: SpecializationUi) : PostFiltersEvent
}