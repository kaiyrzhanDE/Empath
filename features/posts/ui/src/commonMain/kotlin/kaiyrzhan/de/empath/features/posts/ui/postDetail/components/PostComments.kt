package kaiyrzhan.de.empath.features.posts.ui.postDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostCommentsState
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.PostDetailEvent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.model.buildCommentTree
import kaiyrzhan.de.empath.features.posts.ui.posts.components.PostComment
import kaiyrzhan.de.empath.features.posts.ui.posts.components.RenderCommentTree
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ColumnScope.PostComments(
    modifier: Modifier = Modifier,
    state: PostCommentsState,
    onEvent: (PostDetailEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester),
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
                modifier = Modifier
                    .fillMaxWidth(),
                text = buildString {
                    append(stringResource(Res.string.comments))
                    appendColon()
                },
                style = EmpathTheme.typography.headlineSmall,
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

            when (state) {
                is PostCommentsState.Success -> {
                    val comments = remember(state.comments) {
                        state.comments.buildCommentTree()
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        CollapseAnimatedVisibility(
                            visible = state.repliedComment != null,
                        ) {
                            if (state.repliedComment != null) {
                                PostComment(
                                    modifier = Modifier
                                        .bringIntoViewRequester(bringIntoViewRequester)
                                        .fillMaxWidth(),
                                    comment = state.repliedComment,
                                    onLikeClick = {},
                                    onDislikeClick = {},
                                )
                            }
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (state.repliedComment != null) {
                                TextButton(
                                    onClick = { onEvent(PostDetailEvent.CommentReplyCancel) },
                                ) {
                                    Text(
                                        text = stringResource(Res.string.cancel),
                                        style = EmpathTheme.typography.bodyLarge,
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Button(
                                onClick = { onEvent(PostDetailEvent.CommentCreate) },
                                enabled = state.comment.text.isNotBlank(),
                            ) {
                                Text(
                                    text = stringResource(Res.string.send),
                                    style = EmpathTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

                    RenderCommentTree(
                        comments = comments,
                        onReplyClick = { comment ->
                            onEvent(PostDetailEvent.CommentReply(comment))
                            focusRequester.requestFocus()
                        },
                        onLikeClick = { onEvent(PostDetailEvent.CommentLike(it)) },
                        onDislikeClick = { onEvent(PostDetailEvent.CommentDislike(it)) }
                    )
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