package kaiyrzhan.de.empath.features.auth.ui.signUp.model

internal sealed interface SignUpEvent {
    data class NicknameChange(val nickname: String) : SignUpEvent
    data class PasswordChange(val password: String) : SignUpEvent
    data class RepeatedPasswordChange(val password: String) : SignUpEvent
    data object SignUp : SignUpEvent
    data object BackClick : SignUpEvent
    data class UserAgreementAccept(val isAccepted: Boolean) : SignUpEvent
    data object UserAgreementClick : SignUpEvent
    data object PasswordShow : SignUpEvent
    data object RepeatedPasswordShow : SignUpEvent
}