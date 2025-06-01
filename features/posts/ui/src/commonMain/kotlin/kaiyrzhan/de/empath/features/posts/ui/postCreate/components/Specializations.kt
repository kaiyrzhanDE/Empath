package kaiyrzhan.de.empath.features.posts.ui.postCreate.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_arrow_forward
import empath.core.uikit.generated.resources.ic_check
import empath.core.uikit.generated.resources.specialization
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.EmptyResultCard
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateEvent
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateState
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.SpecializationsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Specializations(
    modifier: Modifier = Modifier,
    state: PostCreateState.Success,
    specializationsState: SpecializationsState,
    onEvent: (PostCreateEvent) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "RotateArrow"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.specializationQuery,
            shape = EmpathTheme.shapes.small,
            onValueChange = { query ->
                if(isExpanded.not()) isExpanded = true
                onEvent(PostCreateEvent.SpecializationQueryChange(query))
            },
            textStyle = EmpathTheme.typography.bodyLarge,
            maxLines = 2,
            label = {
                Text(
                    text = stringResource(Res.string.specialization),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            trailingIcon = {
                if (specializationsState is SpecializationsState.Loading) {
                    Box(
                        modifier = Modifier.size(40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            trackColor = EmpathTheme.colors.secondary,
                            strokeCap = StrokeCap.Square,
                            color = EmpathTheme.colors.primary,
                        )
                    }
                } else {
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(rotateAnimation),
                            painter = painterResource(Res.drawable.ic_arrow_forward),
                            contentDescription = null,
                            tint = EmpathTheme.colors.onSurfaceVariant,
                        )
                    }
                }
            }
        )
        if (isExpanded) {
            Text(
                text = buildString {
                    append(stringResource(Res.string.specialization))
                    if (specializationsState is SpecializationsState.Success) {
                        appendSpace()
                        append("(${specializationsState.specializations.size.toGroupedString()})")
                    }
                    appendColon()
                },
            )
        }
        CollapseAnimatedVisibility(visible = isExpanded) {
            Card(
                modifier = Modifier.height(400.dp),
                shape = EmpathTheme.shapes.small,
                border = BorderStroke(1.dp, EmpathTheme.colors.outline),
                colors = CardDefaults.cardColors(
                    containerColor = EmpathTheme.colors.surface,
                    contentColor = EmpathTheme.colors.onSurfaceVariant,
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    when (specializationsState) {
                        is SpecializationsState.Success -> {
                            if (specializationsState.specializations.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    itemsIndexed(specializationsState.specializations) { index, specialization ->
                                        val isSelected =
                                            specialization.id == state.newPost.specialization?.id
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    onEvent(
                                                        if (isSelected) {
                                                            PostCreateEvent.SpecializationRemove
                                                        } else {
                                                            PostCreateEvent.SpecializationSelect(
                                                                specialization
                                                            )
                                                        }
                                                    )
                                                }
                                                .background(EmpathTheme.colors.surfaceContainer)
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Text(
                                                modifier = Modifier.heightIn(min = 24.dp),
                                                text = specialization.name,
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
                                                    contentDescription = null,
                                                    tint = EmpathTheme.colors.secondary
                                                )
                                            }
                                        }
                                        if (index != specializationsState.specializations.lastIndex) {
                                            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                                        }
                                    }
                                }
                            } else {
                                EmptyResultCard(
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }

                        is SpecializationsState.Error -> {
                            ErrorScreen(
                                modifier = Modifier.fillMaxSize(),
                                message = specializationsState.message,
                            )
                        }

                        is SpecializationsState.Loading -> {
                            CircularLoadingCard(
                                modifier = Modifier.fillMaxSize(),
                            )
                        }

                        is SpecializationsState.Initial -> Unit
                    }
                }
            }
        }
    }
}