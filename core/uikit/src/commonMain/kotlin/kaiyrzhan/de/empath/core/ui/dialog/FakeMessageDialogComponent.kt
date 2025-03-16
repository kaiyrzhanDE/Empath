package kaiyrzhan.de.empath.core.ui.dialog

import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeMessageDialogComponent : MessageDialogComponent {
    override val state = MutableStateFlow(
        MessageDialogState(
            title = "Email already registered",
            description = "An account with this email address doesn’t exists.",
            onDismissClick = {},
            onConfirmClick = {},
            confirmActionConfig = MessageActionConfig(
                text = "Use a different email",
                isVisible = true,
            ),
            dismissActionConfig = MessageActionConfig(
                text = "Reset password",
                isPrimary = true,
                isVisible = true,
            )
        )
    )

    override fun onEvent(event: MessageDialogEvent) = Unit
}