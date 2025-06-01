package kaiyrzhan.de.empath.features.posts.ui.model

public enum class Reaction(
    public val type: String,
) {
    DEFAULT(type = "no_reaction"),
    IS_LIKED(type = "is_liked"),
    IS_DISLIKED(type = "is_disliked");

    public fun isLiked(): Boolean{
        return this == IS_LIKED
    }

    public fun isDisliked(): Boolean{
        return this == IS_DISLIKED
    }

    override fun toString(): String {
        return type
    }
}