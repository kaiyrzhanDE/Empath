package kaiyrzhan.de.empath.features.articles.ui.articleСreate.model

internal sealed interface ArticleCreateAction {
    data class ShowSnackbar(val message: String): ArticleCreateAction
}