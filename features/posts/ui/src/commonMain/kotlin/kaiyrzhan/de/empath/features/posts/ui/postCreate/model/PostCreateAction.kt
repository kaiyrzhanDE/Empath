package kaiyrzhan.de.empath.features.posts.ui.postCreate.model

internal sealed interface PostCreateAction {
    data class ShowSnackbar(val message: String): PostCreateAction
}