package kaiyrzhan.de.empath.core.ui.dialog.date_picker.model

public sealed interface DatePickerDialogEvent {
    public data object DismissClick : DatePickerDialogEvent
    public data object ConfirmClick : DatePickerDialogEvent
    public data class DateTimeSelect(val selectedTimeMillis: Long): DatePickerDialogEvent
}