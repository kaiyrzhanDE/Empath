package kaiyrzhan.de.empath.features.articles.ui.tags.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.create_tag
import empath.core.uikit.generated.resources.tag_not_found
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsEvent
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TagCreateCard(
    state: TagsState,
    onEvent: (TagsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = buildString {
                append("\"${state.query}\"")
                appendSpace()
                append(stringResource(Res.string.tag_not_found))
            },
            style = EmpathTheme.typography.bodyLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onEvent(TagsEvent.TagCreate) },
            enabled = state.hasTag().not(),
        ) {
            Text(
                text = stringResource(Res.string.create_tag),
                style = EmpathTheme.typography.labelLarge,
            )
        }
    }
}
