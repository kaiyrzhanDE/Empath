package kaiyrzhan.de.empath.features.auth.ui.password_recovery.model

internal sealed class PasswordRecoveryState {
    object Initial : PasswordRecoveryState()
    object Loading : PasswordRecoveryState()
    class Error(val message: String) : PasswordRecoveryState()
    data class Success(
        val email: String,
        val password: String,
        val isPasswordVisible: Boolean,
        val isPasswordValid: Boolean,
        val repeatedPassword: String,
        val isRepeatedPasswordVisible: Boolean,
        val isRepeatedPasswordValid: Boolean,
        val arePasswordsMatching: Boolean,
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
        fun default(email: String): PasswordRecoveryState {
            return Success(
                email = email,
                password = "",
                isPasswordVisible = false,
                isPasswordValid = true,
                repeatedPassword = "",
                isRepeatedPasswordVisible = false,
                isRepeatedPasswordValid = true,
                arePasswordsMatching = true,
            )
        }
    }
}