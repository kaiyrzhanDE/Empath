package kaiyrzhan.de.empath.core.ui.dialog.model

public sealed interface DialogEvent {
    public data object DismissClick : DialogEvent
    public data object ConfirmClick : DialogEvent
}