package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

public sealed class CodeConfirmationAction {
    public data class ShowSnackbar(val message: String): CodeConfirmationAction()
}