package kaiyrzhan.de.empath.features.auth.ui.signUp.model

internal sealed class SignUpState {
    object Initial : SignUpState()
    object Loading : SignUpState()
    class Error(val message: String) : SignUpState()
    data class Success(
        val email: String,
        val nickname: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isPasswordValid: Boolean = true,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
        val isRepeatedPasswordValid: Boolean = true,
        val isUserAgreementAccepted: Boolean = false,
        val arePasswordsMatching: Boolean = true,
    ) : SignUpState() {
        fun canSignUp(): Boolean = nickname.isNotBlank()
                && password.isNotBlank()
                && repeatedPassword.isNotBlank()
                && isUserAgreementAccepted
                && arePasswordsMatching
                && isPasswordValid
                && isRepeatedPasswordValid
    }
}