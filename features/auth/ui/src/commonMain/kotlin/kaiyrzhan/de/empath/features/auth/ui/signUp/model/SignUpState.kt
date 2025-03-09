package kaiyrzhan.de.empath.features.auth.ui.signUp.model

public sealed class SignUpState {
    internal data object Initial : SignUpState()
    internal data object Loading : SignUpState()
    internal data class Error(val message: String) : SignUpState()
    internal data class Success(
        val email: String,
        val nickname: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val repeatedPassword: String = "",
        val isRepeatedPasswordVisible: Boolean = false,
        val isUserAgreementAccepted: Boolean = false,
        val arePasswordsMatching: Boolean = true,
    ) : SignUpState(){
        fun equalsPasswords() = password == repeatedPassword
    }
}