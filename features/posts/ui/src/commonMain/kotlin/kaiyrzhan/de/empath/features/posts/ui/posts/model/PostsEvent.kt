package kaiyrzhan.de.empath.features.posts.ui.posts.model


internal sealed interface PostsEvent {
    data class PostClick(val postId: String) : PostsEvent
    data object PostCreateClick : PostsEvent
    data class PostDelete(val postId: String) : PostsEvent
    data class PostEdit(val postId: String) : PostsEvent
    data class PostSearch(val query: String) : PostsEvent
    data class PostLike(val postId: String) : PostsEvent
    data class PostDislike(val postId: String) : PostsEvent
    data class PostView(val postId: String) : PostsEvent
    data class PostShare(val postId: String) : PostsEvent
}