package kaiyrzhan.de.empath.features.articles.ui.articles.model


internal data class ArticlesState(
    val query: String? = null,
    val userId: String = "",
    val removedArticlesIds: Set<String> = emptySet(),
    val likedArticlesIds: Set<String> = emptySet(),
    val dislikedArticlesIds: Set<String> = emptySet(),
    val viewedArticlesIds: Set<String> = emptySet(),
) {
    companion object {
        fun default() = ArticlesState()
    }
}

