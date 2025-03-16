package kaiyrzhan.de.empath.core.ui.dialog.model

public sealed class MessageDialogEvent {
    public data object DismissClick : MessageDialogEvent()
    public data object ConfirmClick : MessageDialogEvent()
}