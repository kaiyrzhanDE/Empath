package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

public sealed class CodeConfirmationState {
    internal data object Initial : CodeConfirmationState()
    internal data object Loading : CodeConfirmationState()
    internal data class Error(val message: String) : CodeConfirmationState()
    internal data class Success(
        val email: String,
        val code: String = "",
        val isCodeValid: Boolean = true,
        val isResendAllowed: Boolean = false,
        val resentTimer: Int = -1,
    ) : CodeConfirmationState()
}