package kaiyrzhan.de.empath.features.posts.ui.postFilters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.default
import empath.core.uikit.generated.resources.disliked
import empath.core.uikit.generated.resources.ic_sentiment_dissatisfied
import empath.core.uikit.generated.resources.ic_sentiment_satisfied
import empath.core.uikit.generated.resources.ic_visibility_off
import empath.core.uikit.generated.resources.ic_visibility_on
import empath.core.uikit.generated.resources.liked
import empath.core.uikit.generated.resources.viewed
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersEvent
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun PostReactionTypes(
    modifier: Modifier = Modifier,
    state: PostFiltersState,
    onEvent: (PostFiltersEvent) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        state.reactionTypes.forEach { type ->
            val isSelected = type == state.reactionType
            val color =
                if (isSelected) EmpathTheme.colors.primary else EmpathTheme.colors.onSurface
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(EmpathTheme.shapes.small)
                    .clickable {
                        if (isSelected) {
                            onEvent(PostFiltersEvent.ReactionTypeSelect(PostReactionType.NONE))
                        } else {
                            onEvent(PostFiltersEvent.ReactionTypeSelect(type))
                        }
                    }
                    .thenIf(isSelected) {
                        Modifier.border(
                            width = 1.dp,
                            color = EmpathTheme.colors.primary,
                            shape = EmpathTheme.shapes.small,
                        )
                    }
                    .background(EmpathTheme.colors.surfaceContainer)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(
                        when (type) {
                            PostReactionType.LIKED -> Res.drawable.ic_sentiment_satisfied
                            PostReactionType.DISLIKED -> Res.drawable.ic_sentiment_dissatisfied
                            PostReactionType.VIEWED -> Res.drawable.ic_visibility_on
                            PostReactionType.NONE -> Res.drawable.ic_visibility_off
                        }
                    ),
                    contentDescription = null,
                    tint = color
                )
                Text(
                    text = stringResource(
                        when (type) {
                            PostReactionType.LIKED -> Res.string.liked
                            PostReactionType.DISLIKED -> Res.string.disliked
                            PostReactionType.VIEWED -> Res.string.viewed
                            PostReactionType.NONE -> Res.string.default
                        }
                    ),
                    style = EmpathTheme.typography.bodySmall,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    color = color,
                )
            }
        }
    }
}