package kaiyrzhan.de.empath.features.articles.ui.articles

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesAction
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesEvent
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesState
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ArticlesComponent {

    val state: StateFlow<ArticlesState>

    val articles: Flow<PagingData<ArticleUi>>

    val action: Flow<ArticlesAction>

    fun onEvent(event: ArticlesEvent)

}