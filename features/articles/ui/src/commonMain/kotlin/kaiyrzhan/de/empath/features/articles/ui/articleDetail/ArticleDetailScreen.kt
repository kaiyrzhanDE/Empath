package kaiyrzhan.de.empath.features.articles.ui.articleDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.articleСreate.components.Header
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.components.Article
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.components.ArticleComments
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.components.SubArticle
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.components.TopBar
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleCommentsState
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleDetailEvent
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.model.ArticleDetailState
import kaiyrzhan.de.empath.features.articles.ui.articles.components.ArticleActions

@Composable
internal fun ArticleDetailScreen(
    component: ArticleDetailComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val commentsState = component.commentsState.collectAsState()

    ArticleDetailScreen(
        modifier = modifier,
        state = state.value,
        commentsState = commentsState.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ArticleDetailScreen(
    modifier: Modifier,
    state: ArticleDetailState,
    commentsState: ArticleCommentsState,
    onEvent: (ArticleDetailEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surfaceContainerLow,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is ArticleDetailState.Success -> {
                SelectionContainer {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(contentPadding)
                            .screenHorizontalPadding(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            val maxHeight = maxWidth * 0.15f
                            Header(
                                modifier = Modifier.fillMaxWidth()
                                    .heightIn(min = 40.dp, max = 100.dp)
                                    .height(maxHeight),
                                imageUrl = state.article.author.imageUrl,
                                nickname = state.article.author.nickname,
                                fullName = state.article.author.fullName,
                            )
                        }
                        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                        Article(
                            modifier = Modifier.fillMaxWidth(),
                            article = state.article,
                        )
                        state.article.subArticles.forEach { subArticle ->
                            SubArticle(
                                modifier = Modifier.fillMaxWidth(),
                                subArticle = subArticle,
                            )
                        }
                        ArticleActions(
                            modifier = Modifier.fillMaxWidth(),
                            article = state.article,
                            onLikeClick = { onEvent(ArticleDetailEvent.ArticleLikeClick) },
                            onDislikeClick = { onEvent(ArticleDetailEvent.ArticleDislikeClick) },
                            onShareClick = { onEvent(ArticleDetailEvent.ArticleShare) },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ArticleComments(
                            modifier = Modifier.fillMaxWidth(),
                            state = commentsState,
                            onEvent = onEvent,
                        )
                    }
                }
            }

            is ArticleDetailState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    message = state.message,
                    onTryAgainClick = { onEvent(ArticleDetailEvent.ReloadArticle) },
                )
            }

            is ArticleDetailState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                )
            }

            is ArticleDetailState.Initial -> Unit
        }
    }
}



