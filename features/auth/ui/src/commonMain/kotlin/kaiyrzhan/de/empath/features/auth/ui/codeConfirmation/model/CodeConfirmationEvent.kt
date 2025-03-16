package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

public sealed class CodeConfirmationEvent {
    internal class CodeChange(val code: String): CodeConfirmationEvent()
    internal data object CodeVerify: CodeConfirmationEvent()
    internal data object ResendClick: CodeConfirmationEvent()
    internal data object BackClick: CodeConfirmationEvent()
}