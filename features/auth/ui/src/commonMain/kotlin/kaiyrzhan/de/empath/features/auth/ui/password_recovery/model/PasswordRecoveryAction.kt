package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

internal sealed interface PasswordRecoveryAction {
    class ShowSnackbar(val message: String) : PasswordRecoveryAction
}