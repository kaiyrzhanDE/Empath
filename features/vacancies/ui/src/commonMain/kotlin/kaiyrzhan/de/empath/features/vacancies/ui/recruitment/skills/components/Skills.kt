package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.components

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
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Skills(
    modifier: Modifier = Modifier,
    state: SkillsState,
    skills: LazyPagingItems<SkillUi>,
    onEvent: (SkillsEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = buildString {
                append(stringResource(Res.string.skills))
                if (skills.itemCount != 0) {
                    appendSpace()
                    append("(${skills.itemCount.toGroupedString()})")
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
            when (skills.loadState.refresh) {
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
                        onTryAgainClick = skills::refresh,
                    )
                }

                is LoadState.NotLoading -> {
                    when{
                        skills.itemCount == 0 && state.query.isNotBlank() -> {
                            SkillCreateCard(
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                onEvent = onEvent,
                            )
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                items(skills.itemCount) { index ->
                                    val skill = skills[index]
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        if (skill != null) {
                                            val isSelected = skill in state.editableSkills
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        if (isSelected) {
                                                            onEvent(SkillsEvent.SkillRemove(skill))
                                                        } else {
                                                            onEvent(SkillsEvent.SkillSelect(skill))
                                                        }
                                                    }
                                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = skill.name,
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
                                                        contentDescription = "Tag ${skill.name}",
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
                                        if (index != skills.itemCount) {
                                            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                                        }
                                    }
                                }
                                tagsAppendState(
                                    tags = skills,
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
    tags: LazyPagingItems<SkillUi>,
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