package kaiyrzhan.de.empath.features.articles.ui.articleDetail

import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleCommentsState
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleDetailAction
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleDetailEvent
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleDetailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ArticleDetailComponent {

    val state: StateFlow<ArticleDetailState>

    val commentsState: StateFlow<ArticleCommentsState>

    val action: Flow<ArticleDetailAction>

    fun onEvent(event: ArticleDetailEvent)

}