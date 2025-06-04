package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

import androidx.compose.ui.text.input.TextFieldValue
import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi


internal sealed class PostCommentsState {
    object Initial : PostCommentsState()
    object Loading : PostCommentsState()
    class Error(val message: String) : PostCommentsState()
    data class Success(
        val comments: List<CommentUi> = emptyList(),
        val comment: TextFieldValue = TextFieldValue(
            text = "",
        ),
        val repliedComment: CommentUi? = null,
    ) : PostCommentsState()

    companion object {
        fun default(): PostCommentsState {
            return Initial
        }
    }
}