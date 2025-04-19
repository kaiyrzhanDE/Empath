package kaiyrzhan.de.empath.features.posts.ui.postDetail

import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailAction
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostDetailComponent {

    val state: StateFlow<PostDetailState>

    val commentsState: StateFlow<PostCommentsState>

    val action: Flow<PostDetailAction>

    fun onEvent(event: PostDetailEvent)

}