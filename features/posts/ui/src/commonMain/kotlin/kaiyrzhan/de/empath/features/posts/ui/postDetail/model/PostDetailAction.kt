package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

internal sealed interface PostDetailAction {
    data class ShowSnackbar(val message: String): PostDetailAction
}