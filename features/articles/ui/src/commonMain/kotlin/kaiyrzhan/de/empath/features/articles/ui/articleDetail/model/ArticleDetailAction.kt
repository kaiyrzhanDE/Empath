package kaiyrzhan.de.empath.features.articles.ui.articleDetail.model

internal sealed interface ArticleDetailAction {
    data class ShowSnackbar(val message: String): ArticleDetailAction
}