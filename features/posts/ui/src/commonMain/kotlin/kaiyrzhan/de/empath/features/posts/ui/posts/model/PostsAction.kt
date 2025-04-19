package kaiyrzhan.de.empath.features.posts.ui.posts.model

internal sealed interface PostsAction {
    class ShowSnackbar(val message: String) : PostsAction
}