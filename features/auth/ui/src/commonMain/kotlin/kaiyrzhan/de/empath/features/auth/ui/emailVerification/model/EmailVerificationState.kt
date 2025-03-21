package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

internal sealed class EmailVerificationState {
    object Initial : EmailVerificationState()
    object Loading : EmailVerificationState()
    class Error(val message: String) : EmailVerificationState()
    data class Success(
        val email: String,
        val isEmailValid: Boolean,
    ) : EmailVerificationState()

    companion object {
        fun default(email: String): EmailVerificationState {
            return Success(
                email = email,
                isEmailValid = true,
            )
        }
    }
}