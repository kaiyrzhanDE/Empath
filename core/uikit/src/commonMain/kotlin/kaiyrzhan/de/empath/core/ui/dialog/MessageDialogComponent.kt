package kaiyrzhan.de.empath.core.ui.dialog

import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kotlinx.coroutines.flow.StateFlow

public interface MessageDialogComponent {

    public val state: StateFlow<MessageDialogState>

    public fun onEvent(event: MessageDialogEvent)

    public companion object{
        public const val DEFAULT_KEY: String = "message"
    }

}