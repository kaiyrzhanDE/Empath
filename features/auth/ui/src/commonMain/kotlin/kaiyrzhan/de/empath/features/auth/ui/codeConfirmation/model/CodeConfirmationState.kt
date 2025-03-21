package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

internal sealed class CodeConfirmationState {
    object Initial : CodeConfirmationState()
    object Loading : CodeConfirmationState()
    class Error(val message: String) : CodeConfirmationState()
    data class Success(
        val email: String,
        val code: String = "",
        val isCodeValid: Boolean = true,
        val isResendAllowed: Boolean = false,
        val resentTimer: Int = -1,
    ) : CodeConfirmationState() {
        fun canCheckCode(): Boolean {
            return code.isNotBlank() && isCodeValid
        }
    }

    companion object {
        fun defaultState(email: String): CodeConfirmationState = Success(email = email)
    }
}