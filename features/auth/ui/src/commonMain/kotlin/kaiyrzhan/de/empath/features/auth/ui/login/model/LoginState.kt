package kaiyrzhan.de.empath.features.auth.ui.login.model

public sealed class LoginState {
    public data object Initial : LoginState()
    public data object Loading : LoginState()
    public data class Error(val message: String) : LoginState()
    public data class Success(
        val email: String = "",
        val password: String = "",
    ) : LoginState()
}


