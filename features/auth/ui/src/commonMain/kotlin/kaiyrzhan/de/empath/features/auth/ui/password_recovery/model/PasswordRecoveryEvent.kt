package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

public sealed class PasswordRecoveryEvent {
    internal class PasswordChange(val password: String): PasswordRecoveryEvent()
    internal class RepeatedPasswordChange(val password: String): PasswordRecoveryEvent()
    internal data object PasswordReset: PasswordRecoveryEvent()
    internal data object BackClick: PasswordRecoveryEvent()
    internal data object PasswordShow: PasswordRecoveryEvent()
    internal data object RepeatedPasswordShow: PasswordRecoveryEvent()
}