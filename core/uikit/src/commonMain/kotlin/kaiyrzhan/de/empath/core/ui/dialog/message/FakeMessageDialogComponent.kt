package kaiyrzhan.de.empath.core.ui.dialog.message

import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("unused")
internal class FakeMessageDialogComponent : MessageDialogComponent {
    override val state = MutableStateFlow(
        MessageDialogState(
            title = "Email already registered",
            description = "An account with this email address doesn’t exists.",
            onDismissClick = {},
            onConfirmClick = {},
            confirmActionConfig = DialogActionConfig(
                text = "Use a different email",
                isVisible = true,
            ),
            dismissActionConfig = DialogActionConfig(
                text = "Reset password",
                isPrimary = true,
                isVisible = true,
            )
        )
    )

    override fun onEvent(event: DialogEvent) = Unit
}