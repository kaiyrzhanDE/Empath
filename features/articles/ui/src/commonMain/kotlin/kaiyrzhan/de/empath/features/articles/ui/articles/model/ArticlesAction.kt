package kaiyrzhan.de.empath.features.articles.ui.articles.model

internal sealed interface ArticlesAction {
    class ShowSnackbar(val message: String) : ArticlesAction
}