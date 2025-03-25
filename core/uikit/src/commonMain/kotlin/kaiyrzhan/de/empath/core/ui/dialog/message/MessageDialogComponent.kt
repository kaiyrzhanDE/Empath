package kaiyrzhan.de.empath.core.ui.dialog.message

import kaiyrzhan.de.empath.core.ui.dialog.model.DialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kotlinx.coroutines.flow.StateFlow

public interface MessageDialogComponent {

    public val state: StateFlow<MessageDialogState>

    public fun onEvent(event: DialogEvent)

    public companion object{
        public const val DEFAULT_KEY: String = "message"
    }

}