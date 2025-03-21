package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

internal sealed class PasswordRecoveryState {
    object Initial : PasswordRecoveryState()
    object Loading : PasswordRecoveryState()
    class Error(val message: String) : PasswordRecoveryState()
    data class Success(
        val email: String,
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isPasswordValid: Boolean = true,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
        val isRepeatedPasswordValid: Boolean = true,
        val arePasswordsMatching: Boolean = true,
    ) : PasswordRecoveryState() {
        fun canResetPassword(): Boolean {
            return password.isNotBlank()
                    && repeatedPassword.isNotBlank()
                    && isPasswordValid
                    && isRepeatedPasswordValid
                    && arePasswordsMatching
        }
    }

    companion object {
        fun defaultState(): PasswordRecoveryState = Success(email = "")
    }
}