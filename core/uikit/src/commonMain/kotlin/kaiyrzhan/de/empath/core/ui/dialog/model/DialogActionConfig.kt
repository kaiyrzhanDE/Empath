package kaiyrzhan.de.empath.core.ui.dialog.model

import kotlinx.serialization.Serializable

@Serializable
public data class DialogActionConfig(
    val text: String,
    val isPrimary: Boolean = false,
    val isVisible: Boolean = true,
)