package kaiyrzhan.de.empath.features.posts.ui.tags.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_check
import empath.core.uikit.generated.resources.tags
import empath.core.uikit.generated.resources.unknown_error
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsEvent
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Tags(
    modifier: Modifier = Modifier,
    state: TagsState,
    tags: LazyPagingItems<TagUi>,
    onEvent: (TagsEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = buildString {
                append(stringResource(Res.string.tags))
                if (tags.itemCount != 0) {
                    appendSpace()
                    append("(${tags.itemCount.toGroupedString()})")
                }
                appendColon()
            },
        )
        Card(
            modifier = Modifier
                .heightIn(min = 100.dp, max = 200.dp)
                .fillMaxSize(),
            border = BorderStroke(
                width = 1.dp,
                color = EmpathTheme.colors.outlineVariant,
            ),
            shape = EmpathTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            when (tags.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularLoadingCard(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is LoadState.Error -> {
                    ErrorCard(
                        modifier = Modifier.fillMaxSize(),
                        message = stringResource(Res.string.unknown_error),
                        iconSize = 60.dp,
                        onTryAgainClick = tags::refresh,
                    )
                }

                is LoadState.NotLoading -> {
                    when {
                        tags.itemCount == 0 && state.query.isNotBlank() -> {
                            TagCreateCard(
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                onEvent = onEvent,
                            )
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                items(tags.itemCount) { index ->
                                    val tag = tags[index]
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        if (tag != null) {
                                            val isSelected = tag in state.editableTags
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        if (isSelected) {
                                                            onEvent(TagsEvent.TagRemove(tag))
                                                        } else {
                                                            onEvent(TagsEvent.TagSelect(tag))
                                                        }
                                                    }
                                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = tag.name,
                                                    color = if (isSelected) EmpathTheme.colors.secondary
                                                    else EmpathTheme.colors.onSurface,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = EmpathTheme.typography.labelLarge,
                                                )
                                                if (isSelected) {
                                                    Icon(
                                                        modifier = Modifier.size(24.dp),
                                                        painter = painterResource(Res.drawable.ic_check),
                                                        contentDescription = "Tag ${tag.name}",
                                                        tint = EmpathTheme.colors.secondary
                                                    )
                                                }
                                            }
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp)
                                                    .shimmerLoading(),
                                            )
                                        }
                                        if (index != tags.itemCount) {
                                            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                                        }
                                    }
                                }
                                tagsAppendState(
                                    tags = tags,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

internal fun LazyListScope.tagsAppendState(
    tags: LazyPagingItems<TagUi>,
) {
    when (tags.loadState.append) {
        is LoadState.Error -> {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.unknown_error),
                        color = EmpathTheme.colors.error,
                        style = EmpathTheme.typography.labelLarge,
                    )
                }
            }
        }

        is LoadState.Loading -> {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        trackColor = EmpathTheme.colors.secondary,
                        strokeCap = StrokeCap.Square,
                        color = EmpathTheme.colors.primary,
                    )
                }
            }
        }

        is LoadState.NotLoading -> Unit
    }
}