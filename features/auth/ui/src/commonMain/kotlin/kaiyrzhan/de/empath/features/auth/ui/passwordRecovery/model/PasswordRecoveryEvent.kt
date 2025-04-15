package kaiyrzhan.de.empath.features.auth.ui.passwordRecovery.model

internal sealed interface PasswordRecoveryEvent {
    data class PasswordChange(val password: String) : PasswordRecoveryEvent
    data class RepeatedPasswordChange(val password: String) : PasswordRecoveryEvent
    data object PasswordReset : PasswordRecoveryEvent
    data object BackClick : PasswordRecoveryEvent
    data object PasswordShow : PasswordRecoveryEvent
    data object RepeatedPasswordShow : PasswordRecoveryEvent
}