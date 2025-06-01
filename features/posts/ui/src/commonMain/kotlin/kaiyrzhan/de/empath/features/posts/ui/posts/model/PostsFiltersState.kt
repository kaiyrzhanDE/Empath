package kaiyrzhan.de.empath.features.posts.ui.posts.model


internal data class PostsFiltersState(
    val query: String? = null,
    val userId: String = "",
) {
    companion object {
        fun default() = PostsFiltersState()
    }
}

