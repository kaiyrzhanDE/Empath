package kaiyrzhan.de.empath.features.articles.ui.articles

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.articles.domain.usecase.DeleteArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetArticlesUseCase
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesAction
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesEvent
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesState
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.toUi
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealArticlesComponent(
    componentContext: ComponentContext,
    private val onArticleClick: (articleId: String) -> Unit,
    private val onArticleCreateClick: () -> Unit,
    private val onArticleEditClick: (articleId: String) -> Unit,
) : BaseComponent(componentContext), ArticlesComponent {

    private val getArticlesUseCase: GetArticlesUseCase = get()
    private val deleteArticleUseCase: DeleteArticleUseCase by inject()
    private val getUserUseCase: GetUserUseCase = get()

    override val state = MutableStateFlow(
        ArticlesState.default()
    )

    @OptIn(FlowPreview::class)
    private val queryFlow = state
        .map { state -> state.query }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val articles: Flow<PagingData<ArticleUi>> =
        combine(queryFlow, state) { query, state ->
            query to state
        }.flatMapLatest { (query, state) ->
            getArticlesUseCase(query)
                .map { pagingData ->
                    pagingData
                        .filter { article -> article.id !in state.removedArticlesIds }
                        .map { article ->
                            val id = article.id
                            val isViewed = id in state.viewedArticlesIds
                            val isLiked = id in state.likedArticlesIds
                            val isDisliked = id in state.dislikedArticlesIds

                            article.toUi().copy(
                                isViewed = isViewed,
                                isLiked = isLiked && !isDisliked,
                                viewsCount = article.viewsCount + if (isViewed) 1 else 0,
                                likesCount = article.likesCount + if (isLiked) 1 else 0,
                                dislikesCount = article.dislikesCount + if (isDisliked) 1 else 0,
                            )
                        }
                }
        }.cachedIn(coroutineScope)

    private val _action = Channel<ArticlesAction>(capacity = Channel.BUFFERED)
    override val action: Flow<ArticlesAction> = _action.receiveAsFlow()

    init {
        loadUser()
    }

    override fun onEvent(event: ArticlesEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is ArticlesEvent.ArticleClick -> onArticleClick(event.articleId)
            is ArticlesEvent.ArticleDelete -> deleteArticle(event.articleId)
            is ArticlesEvent.ArticleEdit -> onArticleEditClick(event.articleId)
            is ArticlesEvent.ArticleSearch -> searchArticle(event.query)
            is ArticlesEvent.ArticleLike -> likeArticle(event.articleId)
            is ArticlesEvent.ArticleDislike -> dislikeArticle(event.articleId)
            is ArticlesEvent.ArticleShare -> shareArticle(event.articleId)
            is ArticlesEvent.ArticleView -> viewArticle(event.articleId)
            is ArticlesEvent.ArticleCreateClick -> onArticleCreateClick()
        }
    }

    private fun loadUser() {
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update { currentState ->
                    currentState.copy(
                        userId = user.id
                    )
                }
            }
        }
    }

    private fun searchArticle(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query,
            )
        }
    }

    private fun deleteArticle(articleId: String) {
        coroutineScope.launch {
            deleteArticleUseCase(articleId)
                .onSuccess {
                    state.update { currentState ->
                        currentState.copy(
                            removedArticlesIds = currentState.removedArticlesIds + articleId
                        )
                    }
                }.onFailure { error ->
                    when (error) {
                        is Result.Error.DefaultError -> {
                            _action.send(ArticlesAction.ShowSnackbar(error.toString()))
                        }
                    }
                }
        }
    }

    private fun likeArticle(articleId: String) {
        state.update { currentState ->
            currentState.copy(
                likedArticlesIds = currentState.likedArticlesIds + articleId,
                dislikedArticlesIds = currentState.dislikedArticlesIds
                    .minus(articleId),
            )
        }
    }

    private fun dislikeArticle(articleId: String) {
        state.update { currentState ->
            currentState.copy(
                dislikedArticlesIds = currentState.dislikedArticlesIds + articleId,
                likedArticlesIds = currentState.likedArticlesIds
                    .minus(articleId),
            )
        }
    }

    private fun viewArticle(articleId: String) {
        state.update { currentState ->
            currentState.copy(
                viewedArticlesIds = currentState.viewedArticlesIds + articleId,
            )
        }
    }

    private fun shareArticle(articleId: String) {
        //TODO("Not yet implementataion")
    }
}