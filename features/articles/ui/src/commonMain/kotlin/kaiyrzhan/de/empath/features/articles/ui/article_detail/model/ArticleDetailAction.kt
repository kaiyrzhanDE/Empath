package kaiyrzhan.de.empath.features.articles.ui.article_detail.model

internal sealed interface ArticleDetailAction {
    data class ShowSnackbar(val message: String): ArticleDetailAction
}