package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

internal sealed interface CodeConfirmationEvent {
    data class CodeChange(val code: String) : CodeConfirmationEvent
    data object CodeVerify : CodeConfirmationEvent
    data object ResendClick : CodeConfirmationEvent
    data object BackClick : CodeConfirmationEvent
}