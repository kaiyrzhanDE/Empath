package kaiyrzhan.de.empath.features.auth.ui.signUp.model

internal sealed class SignUpState {
    object Initial : SignUpState()
    object Loading : SignUpState()
    class Error(val message: String) : SignUpState()
    data class Success(
        val email: String,
        val nickname: String,
        val password: String,
        val isPasswordVisible: Boolean,
        val isPasswordValid: Boolean,
        val repeatedPassword: String,
        val isRepeatedPasswordVisible: Boolean,
        val isRepeatedPasswordValid: Boolean,
        val isUserAgreementAccepted: Boolean,
        val arePasswordsMatching: Boolean,
    ) : SignUpState() {
        fun canSignUp(): Boolean = nickname.isNotBlank()
                && password.isNotBlank()
                && repeatedPassword.isNotBlank()
                && isUserAgreementAccepted
                && arePasswordsMatching
                && isPasswordValid
                && isRepeatedPasswordValid
    }

    companion object {
        fun default(email: String): SignUpState {
            return Success(
                email = email,
                nickname = "",
                password = "",
                isPasswordVisible = false,
                isPasswordValid = true,
                repeatedPassword = "",
                isRepeatedPasswordVisible = false,
                isRepeatedPasswordValid = true,
                isUserAgreementAccepted = false,
                arePasswordsMatching = true,
            )
        }
    }
}