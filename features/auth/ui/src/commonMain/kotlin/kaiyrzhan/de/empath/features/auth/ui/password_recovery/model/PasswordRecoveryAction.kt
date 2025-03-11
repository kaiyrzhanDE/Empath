package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

public sealed class PasswordRecoveryAction {
    internal data class ShowSnackbar(val message: String): PasswordRecoveryAction()
}