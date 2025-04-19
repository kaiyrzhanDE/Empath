package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

internal sealed interface PostDetailEvent {
    data object PostLikeClick : PostDetailEvent
    data object PostDislikeClick : PostDetailEvent
    data object EditPost : PostDetailEvent
    data object DeletePost : PostDetailEvent
    data object ReloadPost : PostDetailEvent
    data object BackClick : PostDetailEvent
    data object PostShare : PostDetailEvent

    data class CommentDelete(val commentId: String) : PostDetailEvent
    data class CommentEditMode(val commentId: String) : PostDetailEvent
    data object CommentEdit : PostDetailEvent
    data object CommentCreate : PostDetailEvent
    data object ReloadComments : PostDetailEvent
    data class CommentChange(val comment: String) : PostDetailEvent
}