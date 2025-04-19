package kaiyrzhan.de.empath.features.posts.ui.posts

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsAction
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.model.PostsState
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostsComponent {

    val state: StateFlow<PostsState>

    val posts: Flow<PagingData<PostUi>>

    val action: Flow<PostsAction>

    fun onEvent(event: PostsEvent)

}