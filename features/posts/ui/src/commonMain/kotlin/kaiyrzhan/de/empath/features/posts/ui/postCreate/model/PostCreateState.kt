package kaiyrzhan.de.empath.features.posts.ui.postCreate.model

import kaiyrzhan.de.empath.features.posts.ui.model.postCreate.NewPostUi
import kaiyrzhan.de.empath.features.posts.ui.model.UserUi

internal sealed class PostCreateState {
    object Initial : PostCreateState()
    object Loading : PostCreateState()
    class Error(val message: String) : PostCreateState()
    data class Success(
        val user: UserUi,
        val newPost: NewPostUi,
    ) : PostCreateState()

    companion object {
        fun default(): PostCreateState {
            return Initial
        }
    }
}