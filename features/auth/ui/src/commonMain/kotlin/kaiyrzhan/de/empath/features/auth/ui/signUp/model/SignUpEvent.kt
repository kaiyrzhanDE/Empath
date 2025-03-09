package kaiyrzhan.de.empath.features.auth.ui.signUp.model

public sealed class SignUpEvent {
    internal class NicknameChange(val nickname: String): SignUpEvent()
    internal class PasswordChange(val password: String): SignUpEvent()
    internal class RepeatedPasswordChange(val password: String): SignUpEvent()
    internal data object SignUp: SignUpEvent()
    internal data object BackClick: SignUpEvent()
    internal data class UserAgreementAccept(val isAccepted: Boolean): SignUpEvent()
    internal data object UserAgreementClick: SignUpEvent()
    internal data object PasswordShow: SignUpEvent()
    internal data object RepeatedPasswordShow: SignUpEvent()
}