package kaiyrzhan.de.empath.features.auth.ui.login.model

public sealed class LoginEvent {
    internal class EmailChange(val email: String) : LoginEvent()
    internal class PasswordChange(val password: String) : LoginEvent()
    internal data object LogIn : LoginEvent()
    internal data object SignUp : LoginEvent()
    internal data object GoogleAuthClick : LoginEvent()
    internal data object FacebookAuthClick : LoginEvent()
    internal data object ResetPassword : LoginEvent()
}
