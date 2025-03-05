package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

public sealed class CodeConfirmationState {
    internal data object Initial : CodeConfirmationState()
    internal data object Loading : CodeConfirmationState()
    internal data class Error(val message: String) : CodeConfirmationState()
    internal data class Success(
        val email: String,
        val code: String = "",
        val isResendAllowed: Boolean,
        val resentTimer: Int,
    ) : CodeConfirmationState()
}