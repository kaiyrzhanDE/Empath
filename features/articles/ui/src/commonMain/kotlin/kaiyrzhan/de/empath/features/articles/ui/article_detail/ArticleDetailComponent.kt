package kaiyrzhan.de.empath.features.articles.ui.article_detail

import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleCommentsState
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailAction
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailEvent
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ArticleDetailComponent {

    val state: StateFlow<ArticleDetailState>

    val commentsState: StateFlow<ArticleCommentsState>

    val action: Flow<ArticleDetailAction>

    fun onEvent(event: ArticleDetailEvent)

}