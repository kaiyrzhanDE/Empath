package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

internal sealed interface CodeConfirmationAction {
    class ShowSnackbar(val message: String) : CodeConfirmationAction
}