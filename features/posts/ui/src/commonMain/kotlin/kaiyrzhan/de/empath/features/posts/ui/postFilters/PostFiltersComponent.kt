package kaiyrzhan.de.empath.features.posts.ui.postFilters

import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersAction
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersEvent
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersState
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.SpecializationsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostFiltersComponent {
    val state: StateFlow<PostFiltersState>

    val specializationsState: StateFlow<SpecializationsState>

    val action: Flow<PostFiltersAction>

    fun onEvent(event: PostFiltersEvent)
}