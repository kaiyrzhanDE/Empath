package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

public sealed class PasswordRecoveryState {
    internal data object Initial : PasswordRecoveryState()
    internal data object Loading : PasswordRecoveryState()
    internal data class Error(val message: String) : PasswordRecoveryState()
    internal data class Success(
        val email: String,
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
        val arePasswordsMatching: Boolean = true,
    ) : PasswordRecoveryState(){
        fun equalsPasswords() = password == repeatedPassword
    }
}