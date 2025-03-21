package kaiyrzhan.de.empath.features.auth.ui.login.model

internal sealed interface LoginEvent {
    data class EmailChange(val email: String) : LoginEvent
    data class PasswordChange(val password: String) : LoginEvent
    data object LogIn : LoginEvent
    data object SignUp : LoginEvent
    data object GoogleAuthClick : LoginEvent
    data object FacebookAuthClick : LoginEvent
    data object ResetPassword : LoginEvent
}
