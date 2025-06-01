package kaiyrzhan.de.empath.features.posts.ui.posts

import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsFiltersState
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostsComponent {

    val filtersState: StateFlow<PostsFiltersState>

    val state: StateFlow<PostsState>

    val action: Flow<PostsAction>

    fun onEvent(event: PostsEvent)

}