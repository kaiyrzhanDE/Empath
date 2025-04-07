package kaiyrzhan.de.empath.features.articles.ui.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsEvent
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsState
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kaiyrzhan.de.empath.features.articles.ui.tags.components.SelectedTags
import kaiyrzhan.de.empath.features.articles.ui.tags.components.Tags
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TagsDialog(
    component: TagsDialogComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val tags = component.tags.collectAsLazyPagingItems()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        TagsBottomSheetDialog(
            modifier = Modifier,
            tags = tags,
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        TagsDialog(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            tags = tags,
            state = state.value,
            onEvent = component::onEvent,
        )
    }
}

@Composable
private fun TagsDialog(
    modifier: Modifier,
    state: TagsState,
    tags: LazyPagingItems<TagUi>,
    onEvent: (TagsEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(TagsEvent.DismissClick) },
    ) {
        Card(
            shape = EmpathTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            TagsDialogContent(
                modifier = modifier,
                state = state,
                tags = tags,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagsBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: TagsState,
    tags: LazyPagingItems<TagUi>,
    onEvent: (TagsEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                onEvent(TagsEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        TagsDialogContent(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state,
            tags = tags,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun TagsDialogContent(
    modifier: Modifier = Modifier,
    state: TagsState,
    tags: LazyPagingItems<TagUi>,
    onEvent: (TagsEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(Res.string.adding_tags_title),
                style = EmpathTheme.typography.titleLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = stringResource(Res.string.adding_tags_description),
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
        SelectedTags(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onEvent = onEvent,
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.query,
            shape = EmpathTheme.shapes.small,
            onValueChange = { query ->
                onEvent(TagsEvent.Search(query))
            },
            enabled = state.isQueryValidLength(),
            label = {
                Text(
                    text = stringResource(Res.string.tag_name),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )
        Tags(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            tags = tags,
            onEvent = onEvent,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            TextButton(
                onClick = { onEvent(TagsEvent.DismissClick) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(
                    text = stringResource(Res.string.close),
                    style = EmpathTheme.typography.labelLarge,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onEvent(TagsEvent.TagsSelectClick) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.select_tags),
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }
    }
}

