package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

import androidx.compose.ui.text.input.TextFieldValue
import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi

internal sealed interface PostDetailEvent {
    data object PostLikeClick : PostDetailEvent
    data object PostDislikeClick : PostDetailEvent
    data object EditPost : PostDetailEvent
    data object DeletePost : PostDetailEvent
    data object ReloadPost : PostDetailEvent
    data object BackClick : PostDetailEvent
    data object PostShare : PostDetailEvent
    data object PostView : PostDetailEvent

    data class CommentDelete(val commentId: String) : PostDetailEvent
    data object CommentEdit : PostDetailEvent
    data object CommentCreate : PostDetailEvent
    data class CommentLike(val comment: CommentUi) : PostDetailEvent
    data class CommentReply(val comment: CommentUi) : PostDetailEvent
    data object CommentReplyCancel : PostDetailEvent
    data class CommentDislike(val comment: CommentUi) : PostDetailEvent
    data object ReloadComments : PostDetailEvent
    data class CommentChange(val comment: TextFieldValue) : PostDetailEvent
}