package kaiyrzhan.de.empath.features.posts.ui.postEdit.model

import kaiyrzhan.de.empath.features.posts.ui.model.postEdit.EditedPostUi

internal sealed class PostEditState {
    object Initial : PostEditState()
    object Loading : PostEditState()
    class Error(val message: String) : PostEditState()
    data class Success(
        val editablePost: EditedPostUi,
        val originalPost: EditedPostUi,
    ) : PostEditState()

    companion object {
        fun default(): PostEditState {
            return Initial
        }
    }
}