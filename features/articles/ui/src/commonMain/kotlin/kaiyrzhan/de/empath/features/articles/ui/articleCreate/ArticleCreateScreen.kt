package kaiyrzhan.de.empath.features.articles.ui.articleСreate

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.ArticleCreateComponent
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.components.TopBar
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.model.ArticleCreateAction
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.model.ArticleCreateEvent
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.model.ArticleCreateState
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.components.Article
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.components.Header
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.components.SubArticle
import kaiyrzhan.de.empath.features.articles.ui.tags.TagsDialog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi

@Composable
internal fun ArticleCreateScreen(
    component: ArticleCreateComponent,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val state = component.state.collectAsState()

    val messageDialogSlot by component.messageDialog.subscribeAsState()
    messageDialogSlot.child?.instance?.also { messageComponent ->
        MessageDialog(
            component = messageComponent,
        )
    }

    val tagsDialogSlot by component.tagsDialog.subscribeAsState()
    tagsDialogSlot.child?.instance?.also { tagsComponent ->
        TagsDialog(
            component = tagsComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is ArticleCreateAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    ArticleCreateScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
private fun ArticleCreateScreen(
    modifier: Modifier = Modifier,
    state: ArticleCreateState,
    onEvent: (ArticleCreateEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surfaceContainerLow,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is ArticleCreateState.Success -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .animateContentSize()
                        .fillMaxSize()
                        .padding(contentPadding)
                        .screenPadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val maxHeight = maxWidth * 0.15f
                        Header(
                            modifier = Modifier.fillMaxWidth()
                                .heightIn(min = 40.dp, max = 100.dp)
                                .height(maxHeight),
                            fullName = state.user.getFullName(),
                            imageUrl = state.user.imageUrl,
                            nickname = state.user.nickname,
                        )
                    }
                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    Article(
                        article = state.newArticle,
                        onEvent = onEvent,
                    )
                    state.newArticle.subArticles.forEachIndexed { position, subArticle ->
                        SubArticle(
                            modifier = Modifier.fillMaxWidth(),
                            subArticle = subArticle,
                            position = position,
                            onEvent = onEvent,
                        )
                    }
                    Column(
                        modifier = modifier
                            .clip(EmpathTheme.shapes.small)
                            .border(
                                width = 1.dp,
                                color = EmpathTheme.colors.outlineVariant,
                                shape = EmpathTheme.shapes.small,
                            )
                            .background(EmpathTheme.colors.surface)
                            .clickable { onEvent(ArticleCreateEvent.SubArticleAdd) }
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(Res.string.add_sub_article),
                            style = EmpathTheme.typography.labelLarge,
                            color = EmpathTheme.colors.primary,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .defaultMaxWidth()
                            .align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .animateContentSize(),
                            onClick = { onEvent(ArticleCreateEvent.ArticleClear) },
                            shape = EmpathTheme.shapes.small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmpathTheme.colors.surfaceContainer,
                                contentColor = EmpathTheme.colors.onSurface,
                            ),
                        ) {
                            Text(
                                text = stringResource(Res.string.cancel),
                                style = EmpathTheme.typography.labelLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { onEvent(ArticleCreateEvent.ArticleCreate) },
                            shape = EmpathTheme.shapes.small,
                            enabled = state.newArticle.isChanged(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmpathTheme.colors.primary,
                                contentColor = EmpathTheme.colors.onPrimary,
                            ),
                        ) {
                            Text(
                                text = stringResource(Res.string.send),
                                style = EmpathTheme.typography.labelLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }

            is ArticleCreateState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is ArticleCreateState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = { onEvent(ArticleCreateEvent.LoadUser) },
                )
            }

            is ArticleCreateState.Initial -> Unit
        }
    }
}

