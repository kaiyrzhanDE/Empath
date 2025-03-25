package kaiyrzhan.de.empath.core.ui.dialog.message.model

import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kotlinx.serialization.Serializable

@Serializable
public data class MessageDialogState(
    val title: String,
    val description: String? = null,
    val dismissActionConfig: DialogActionConfig,
    val confirmActionConfig: DialogActionConfig? = null,
    val onDismissClick: () -> Unit,
    val onConfirmClick: (() -> Unit)? = null,
)