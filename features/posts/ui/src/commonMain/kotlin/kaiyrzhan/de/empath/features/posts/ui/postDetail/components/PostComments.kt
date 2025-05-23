package kaiyrzhan.de.empath.features.posts.ui.postDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
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
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostComment
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.PostComments(
    modifier: Modifier = Modifier,
    state: PostCommentsState,
    onEvent: (PostDetailEvent) -> Unit,
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
                is PostCommentsState.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.comment,
                            onValueChange = { comment ->
                                onEvent(PostDetailEvent.CommentChange(comment))
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
                                onSend = { onEvent(PostDetailEvent.CommentCreate) },
                            ),
                            textStyle = EmpathTheme.typography.bodyLarge,
                            shape = EmpathTheme.shapes.small,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
                            )
                        )
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = { onEvent(PostDetailEvent.CommentCreate) },
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
                        PostComment(
                            modifier = Modifier.fillMaxWidth(),
                            nickname = comment.author.nickname,
                            fullName = comment.author.fullName,
                            imageUrl = comment.author.imageUrl,
                            comment = comment.text,
                        )
                    }
                }

                is PostCommentsState.Error -> {
                    ErrorCard(
                        modifier = Modifier.fillMaxWidth(),
                        message = state.message,
                        onTryAgainClick = {
                            onEvent(PostDetailEvent.ReloadComments)
                        },
                    )
                }

                is PostCommentsState.Loading -> {
                    CircularLoadingCard(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                is PostCommentsState.Initial -> Unit
            }
        }
    }

}