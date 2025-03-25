package kaiyrzhan.de.empath.core.ui.dialog.date_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kaiyrzhan.de.empath.core.ui.dialog.components.DialogActionButton
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toLong
import kotlinx.coroutines.launch

@Composable
public fun DatePickerDialog(
    component: DatePickerComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        DatePickerBottomSheetDialog(
            modifier = modifier,
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        DatePickerModalDialog(
            modifier = modifier,
            state = state.value,
            onEvent = component::onEvent,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerModalDialog(
    modifier: Modifier,
    state: DatePickerDialogState,
    onEvent: (DatePickerDialogEvent) -> Unit,
) {
    Box(
        modifier = Modifier.padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.material3.DatePickerDialog(
            modifier = modifier.padding(24.dp),
            onDismissRequest = { onEvent(DatePickerDialogEvent.DismissClick) },
            shape = EmpathTheme.shapes.medium,
            dismissButton = {
                DialogActionButton(
                    config = state.dismissActionConfig,
                    onClick = { onEvent(DatePickerDialogEvent.DismissClick) },
                )
            },
            confirmButton = {
                DialogActionButton(
                    config = state.confirmActionConfig,
                    onClick = { onEvent(DatePickerDialogEvent.ConfirmClick) },
                )
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
            colors = datePickerColors(),
        ) {
            DatePickerContent(
                modifier = Modifier
                    .fillMaxSize(),
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: DatePickerDialogState,
    onEvent: (DatePickerDialogEvent) -> Unit,
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
                onEvent(DatePickerDialogEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            DatePickerContent(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                onEvent = onEvent,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                DialogActionButton(
                    config = state.dismissActionConfig,
                    onClick = { onEvent(DatePickerDialogEvent.DismissClick) },
                )
                Spacer(modifier = Modifier.width(8.dp))
                DialogActionButton(
                    config = state.confirmActionConfig,
                    onClick = { onEvent(DatePickerDialogEvent.ConfirmClick) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun datePickerColors(): DatePickerColors {
    return DatePickerDefaults.colors(
        containerColor = EmpathTheme.colors.surface,
        navigationContentColor = EmpathTheme.colors.onSurfaceVariant,
        titleContentColor = EmpathTheme.colors.onSurfaceVariant,
        headlineContentColor = EmpathTheme.colors.onSurface,
        weekdayContentColor = EmpathTheme.colors.onSurfaceVariant,
        subheadContentColor = EmpathTheme.colors.onSurfaceVariant,
        dayContentColor = EmpathTheme.colors.onSurface,
        yearContentColor = EmpathTheme.colors.onSurfaceVariant,
        todayContentColor = EmpathTheme.colors.primary,
        currentYearContentColor = EmpathTheme.colors.onSurface,
        todayDateBorderColor = EmpathTheme.colors.outlineVariant,
        dividerColor = EmpathTheme.colors.outlineVariant,
        selectedYearContentColor = EmpathTheme.colors.onSurface,
        selectedDayContainerColor = EmpathTheme.colors.primary,
        selectedDayContentColor = EmpathTheme.colors.onPrimary,
        disabledSelectedDayContainerColor = EmpathTheme.colors.surface,
        disabledSelectedYearContainerColor = EmpathTheme.colors.surface,
        disabledDayContentColor = EmpathTheme.colors.outlineVariant,
        disabledSelectedDayContentColor = EmpathTheme.colors.outlineVariant,
        disabledSelectedYearContentColor = EmpathTheme.colors.outlineVariant,
        disabledYearContentColor = EmpathTheme.colors.outlineVariant,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerContent(
    modifier: Modifier = Modifier,
    state: DatePickerDialogState,
    onEvent: (DatePickerDialogEvent) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate.toLong(),
        initialDisplayMode = state.type.toMode(),
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            onEvent(DatePickerDialogEvent.DateTimeSelect(millis))
        }
    }

    DatePicker(
        modifier = modifier,
        state = datePickerState,
        showModeToggle = state.showModeToggle,
        colors = datePickerColors(),
    )
}