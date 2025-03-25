package kaiyrzhan.de.empath.core.ui.dialog.message

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kotlinx.coroutines.flow.MutableStateFlow

public class RealMessageDialogComponent(
    componentContext: ComponentContext,
    private val messageDialogState: MessageDialogState,
) : BaseComponent(componentContext), MessageDialogComponent {

    override val state: MutableStateFlow<MessageDialogState> = MutableStateFlow(messageDialogState)

    override fun onEvent(event: DialogEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is DialogEvent.DismissClick -> messageDialogState.onDismissClick()
            is DialogEvent.ConfirmClick -> messageDialogState.onConfirmClick?.invoke()
        }
    }
}