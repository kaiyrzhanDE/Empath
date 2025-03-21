package kaiyrzhan.de.empath.features.auth.ui.login.model

internal sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    class Error(val message: String) : LoginState()
    data class Success(
        val email: String = "",
        val isEmailValid: Boolean = true,
        val password: String = "",
        val isPasswordValid: Boolean = true,
    ) : LoginState()
}


