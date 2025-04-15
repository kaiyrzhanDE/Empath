package kaiyrzhan.de.empath.features.articles.ui.articleCreate.model

internal sealed interface ArticleCreateAction {
    data class ShowSnackbar(val message: String): ArticleCreateAction
}