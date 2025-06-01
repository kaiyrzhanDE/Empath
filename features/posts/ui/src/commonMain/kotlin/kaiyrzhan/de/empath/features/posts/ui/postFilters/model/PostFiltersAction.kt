package kaiyrzhan.de.empath.features.posts.ui.postFilters.model

internal sealed interface PostFiltersAction {
    data class ShowSnackbar(val message: String): PostFiltersAction
}