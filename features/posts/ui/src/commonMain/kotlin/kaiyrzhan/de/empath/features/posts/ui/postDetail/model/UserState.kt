package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

internal data class UserState(
    val userEmail: String,
    val userId: String,
) {
    companion object {
        fun default(): UserState {
            return UserState(
                userEmail = "",
                userId = "",
            )
        }
    }
}