package kaiyrzhan.de.empath.features.auth.ui.login.model

public sealed class LoginState {
    public data object Initial : LoginState()
    public class Success() : LoginState()
}
