package kaiyrzhan.de.empath.core.ui.dialog.date_picker.model

import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
public data class DatePickerDialogState(
    val selectedDate: LocalDateTime? = null,
    val showModeToggle: Boolean = false,
    val type: PickerType = PickerType.PICKER,
    val dismissActionConfig: DialogActionConfig,
    val confirmActionConfig: DialogActionConfig,
    val onDismissClick: () -> Unit,
    val onConfirmClick: (selectedDate: LocalDateTime?) -> Unit,
)

