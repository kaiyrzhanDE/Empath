package kaiyrzhan.de.empath.core.ui.dialog.date_picker

import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("unused")
internal class FakeDatePickerComponent: DatePickerComponent {
    override val state = MutableStateFlow(
        DatePickerDialogState(
            onDismissClick = {},
            onConfirmClick = {},
            confirmActionConfig = DialogActionConfig(
                text = "Okay",
                isVisible = true,
            ),
            dismissActionConfig = DialogActionConfig(
                text = "Close",
                isPrimary = true,
                isVisible = true,
            )
        )
    )

    override fun onEvent(event: DatePickerDialogEvent) = Unit
}