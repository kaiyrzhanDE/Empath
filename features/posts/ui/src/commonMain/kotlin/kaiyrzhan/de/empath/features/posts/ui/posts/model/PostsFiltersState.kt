package kaiyrzhan.de.empath.features.posts.ui.posts.model

import kaiyrzhan.de.empath.features.posts.ui.model.PostFiltersUi


internal data class PostsFiltersState(
    val filters: PostFiltersUi,
    val userId: String,
) {
    companion object {
        fun default(): PostsFiltersState {
            return PostsFiltersState(
                filters = PostFiltersUi(),
                userId = "",
            )
        }
    }
}

