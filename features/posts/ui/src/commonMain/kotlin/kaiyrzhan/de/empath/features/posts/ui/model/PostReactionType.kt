package kaiyrzhan.de.empath.features.posts.ui.model

internal enum class PostReactionType {
    NONE,
    LIKED,
    DISLIKED,
    VIEWED;

    fun isLiked(): Boolean {
        return this == LIKED
    }
    fun isDisliked(): Boolean {
        return this == DISLIKED
    }
    fun isViewed(): Boolean {
        return this == VIEWED
    }
}