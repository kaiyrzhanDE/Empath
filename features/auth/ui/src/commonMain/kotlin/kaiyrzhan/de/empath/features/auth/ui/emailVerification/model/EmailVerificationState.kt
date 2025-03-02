package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

public sealed class EmailVerificationState {
    internal data object Initial : EmailVerificationState()
    internal data object Loading : EmailVerificationState()
    internal data class Error(val message: String) : EmailVerificationState()
    internal data class Success(
        val email: String = "",
    ) : EmailVerificationState()
}