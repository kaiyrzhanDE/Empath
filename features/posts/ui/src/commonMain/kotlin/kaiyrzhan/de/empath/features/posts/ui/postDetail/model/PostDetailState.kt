package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

import kaiyrzhan.de.empath.features.posts.ui.model.PostUi

internal sealed class PostDetailState {
    object Initial : PostDetailState()
    object Loading : PostDetailState()
    class Error(val message: String) : PostDetailState()
    data class Success(
        val post: PostUi,
    ) : PostDetailState()

    companion object {
        fun default(): PostDetailState {
            return Initial
        }
    }
}
