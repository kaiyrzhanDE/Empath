package kaiyrzhan.de.empath.features.posts.ui.posts.model

import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostFiltersUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostUi


internal sealed interface PostsEvent {
    data class PostClick(val postId: String) : PostsEvent
    data object PostCreateClick : PostsEvent
    data object PostFiltersClick : PostsEvent
    data object FavouritePostsClick : PostsEvent
    data object LoadPosts : PostsEvent
    data object ReloadPosts : PostsEvent
    data class ApplyPostFilters(val filters: PostFiltersUi) : PostsEvent
    data class PostDelete(val postId: String) : PostsEvent
    data class PostEdit(val postId: String) : PostsEvent
    data class PostSearch(val query: String) : PostsEvent
    data class PostLike(val post: PostUi) : PostsEvent
    data class PostDislike(val post: PostUi) : PostsEvent
    data class PostShare(val post: PostUi) : PostsEvent
}