package kaiyrzhan.de.empath.features.posts.ui.posts.model

import kaiyrzhan.de.empath.features.posts.ui.model.PostUi

internal sealed class PostsState {
    data object Initial : PostsState()
    data object Loading : PostsState()
    data class Error(val message: String) : PostsState()
    data class Success(
        val posts: List<PostUi>,
    ) : PostsState()

    companion object {
        fun default(): PostsState {
            return Initial
        }
    }
}