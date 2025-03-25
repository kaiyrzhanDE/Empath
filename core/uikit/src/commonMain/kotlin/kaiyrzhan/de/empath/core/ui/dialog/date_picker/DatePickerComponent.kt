package kaiyrzhan.de.empath.core.ui.dialog.date_picker

import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kotlinx.coroutines.flow.StateFlow

public interface DatePickerComponent {

    public val state: StateFlow<DatePickerDialogState>

    public fun onEvent(event: DatePickerDialogEvent)

    public companion object{
        public const val DEFAULT_KEY: String = "datePicker"
    }

}