package kaiyrzhan.de.empath.features.posts.ui.postEdit.model

internal sealed interface PostEditAction {
    data class ShowSnackbar(val message: String): PostEditAction
}