package kaiyrzhan.de.empath.features.posts.ui.posts.model


internal data class PostsState(
    val query: String? = null,
    val userId: String = "",
    val removedPostsIds: Set<String> = emptySet(),
    val likedPostsIds: Set<String> = emptySet(),
    val dislikedPostsIds: Set<String> = emptySet(),
    val viewedPostsIds: Set<String> = emptySet(),
) {
    companion object {
        fun default() = PostsState()
    }
}

