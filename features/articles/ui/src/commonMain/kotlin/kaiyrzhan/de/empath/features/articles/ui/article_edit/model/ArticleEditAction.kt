package kaiyrzhan.de.empath.features.articles.ui.article_edit.model

internal sealed interface ArticleEditAction {
    data class ShowSnackbar(val message: String): ArticleEditAction
}