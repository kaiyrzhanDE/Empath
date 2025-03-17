package kaiyrzhan.de.empath.core.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kaiyrzhan.de.empath.core.ui.dialog.components.DialogActionButton
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kotlinx.coroutines.launch

@Composable
public fun MessageDialog(
    component: MessageDialogComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        MessageBottomSheetDialog(
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        MessageDialog(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp),
            state = state.value,
            onEvent = component::onEvent,
        )
    }
}

@Composable
private fun MessageDialog(
    modifier: Modifier,
    state: MessageDialogState,
    onEvent: (MessageDialogEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(MessageDialogEvent.DismissClick) },
    ) {
        Card(
            shape = EmpathTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            MessageDialogContent(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: MessageDialogState,
    onEvent: (MessageDialogEvent) -> Unit,
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
                onEvent(MessageDialogEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        MessageDialogContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            state = state,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun MessageDialogContent(
    modifier: Modifier,
    state: MessageDialogState,
    onEvent: (MessageDialogEvent) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = state.title,
            style = EmpathTheme.typography.titleLarge,
            color = EmpathTheme.colors.onSurface,
        )
        if (!state.description.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.description,
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            DialogActionButton(
                config = state.confirmActionConfig,
                onClick = { onEvent(MessageDialogEvent.ConfirmClick) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DialogActionButton(
                config = state.dismissActionConfig,
                onClick = { onEvent(MessageDialogEvent.DismissClick) },
            )
        }
    }
}