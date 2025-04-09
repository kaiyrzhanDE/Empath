package kaiyrzhan.de.empath.features.articles.ui.article_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleCommentsState
import kaiyrzhan.de.empath.features.articles.ui.article_detail.model.ArticleDetailEvent
import kaiyrzhan.de.empath.features.articles.ui.articles.components.ArticleComment
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.ArticleComments(
    modifier: Modifier = Modifier,
    state: ArticleCommentsState,
    onEvent: (ArticleDetailEvent) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            contentColor = EmpathTheme.colors.onSurface,
            containerColor = EmpathTheme.colors.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .screenPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildString {
                    append(stringResource(Res.string.comments))
                    appendColon()
                },
                style = EmpathTheme.typography.headlineSmall,
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

            when (state) {
                is ArticleCommentsState.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.comment,
                            onValueChange = { comment ->
                                onEvent(ArticleDetailEvent.CommentChange(comment))
                            },
                            label = {
                                Text(
                                    text = stringResource(Res.string.comment),
                                    style = EmpathTheme.typography.bodyLarge,
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Send,
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = { onEvent(ArticleDetailEvent.CommentCreate) },
                            ),
                            textStyle = EmpathTheme.typography.bodyLarge,
                            shape = EmpathTheme.shapes.small,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
                            )
                        )
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = { onEvent(ArticleDetailEvent.CommentCreate) },
                            enabled = state.comment.isNotBlank(),
                        ) {
                            Text(
                                text = stringResource(Res.string.send),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        }
                    }
                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    state.comments.forEach { comment ->
                        ArticleComment(
                            modifier = Modifier.fillMaxWidth(),
                            nickname = comment.author.nickname,
                            fullName = comment.author.fullName,
                            imageUrl = comment.author.imageUrl,
                            comment = comment.text,
                        )
                    }
                }

                is ArticleCommentsState.Error -> {
                    ErrorCard(
                        modifier = Modifier.fillMaxWidth(),
                        message = state.message,
                        onTryAgainClick = {
                            onEvent(ArticleDetailEvent.ReloadComments)
                        },
                    )
                }

                is ArticleCommentsState.Loading -> {
                    CircularLoadingCard(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                is ArticleCommentsState.Initial -> Unit
            }
        }
    }

}