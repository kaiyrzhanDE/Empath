package kaiyrzhan.de.empath.features.auth.ui.passwordRecovery.model

internal sealed interface PasswordRecoveryAction {
    class ShowSnackbar(val message: String) : PasswordRecoveryAction
}