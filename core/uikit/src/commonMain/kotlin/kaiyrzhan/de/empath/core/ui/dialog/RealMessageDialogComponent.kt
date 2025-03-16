package kaiyrzhan.de.empath.core.ui.dialog

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kotlinx.coroutines.flow.MutableStateFlow

public class RealMessageDialogComponent(
    private val componentContext: ComponentContext,
    private val dialogState: MessageDialogState,
) : MessageDialogComponent, ComponentContext by componentContext {

    override val state: MutableStateFlow<MessageDialogState> = MutableStateFlow(dialogState)

    override fun onEvent(event: MessageDialogEvent) {
        when (event) {
            is MessageDialogEvent.DismissClick -> dialogState.onDismissClick()
            is MessageDialogEvent.ConfirmClick -> dialogState.onConfirmClick?.invoke()
        }
    }
}