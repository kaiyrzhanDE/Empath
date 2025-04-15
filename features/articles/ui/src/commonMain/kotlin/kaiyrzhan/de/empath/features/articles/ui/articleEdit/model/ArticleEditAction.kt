package kaiyrzhan.de.empath.features.articles.ui.articleEdit.model

internal sealed interface ArticleEditAction {
    data class ShowSnackbar(val message: String): ArticleEditAction
}