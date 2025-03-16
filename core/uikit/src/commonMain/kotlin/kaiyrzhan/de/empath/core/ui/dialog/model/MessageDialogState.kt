package kaiyrzhan.de.empath.core.ui.dialog.model

import kotlinx.serialization.Serializable

@Serializable
public data class MessageDialogState(
    val title: String,
    val description: String? = null,
    val dismissActionConfig: MessageActionConfig,
    val confirmActionConfig: MessageActionConfig? = null,
    val onDismissClick: () -> Unit,
    val onConfirmClick: (() -> Unit)? = null,
)