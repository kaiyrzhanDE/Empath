package kaiyrzhan.de.empath.core.ui.dialog.date_picker

import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeDatePickerComponent: DatePickerComponent {
    override val state = MutableStateFlow(
        DatePickerDialogState(
            onDismissClick = {},
            onConfirmClick = {},
            confirmActionConfig = DialogActionConfig(
                text = "Use a different email",
                isVisible = true,
            ),
            dismissActionConfig = DialogActionConfig(
                text = "Reset password",
                isPrimary = true,
                isVisible = true,
            )
        )
    )

    override fun onEvent(event: DatePickerDialogEvent) = Unit
}