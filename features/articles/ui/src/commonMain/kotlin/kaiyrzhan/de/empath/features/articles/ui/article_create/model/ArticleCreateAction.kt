package kaiyrzhan.de.empath.features.articles.ui.article_create.model

internal sealed interface ArticleCreateAction {
    data class ShowSnackbar(val message: String): ArticleCreateAction
}