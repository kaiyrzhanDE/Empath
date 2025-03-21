package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model

internal sealed class CodeConfirmationState {
    object Initial : CodeConfirmationState()
    object Loading : CodeConfirmationState()
    class Error(val message: String) : CodeConfirmationState()
    data class Success(
        val email: String,
        val code: String,
        val isCodeValid: Boolean,
        val isResendAllowed: Boolean,
        val resentTimer: Int,
    ) : CodeConfirmationState() {
        fun canCheckCode(): Boolean {
            return code.isNotBlank() && isCodeValid
        }
    }

    companion object {
        fun default(email: String): CodeConfirmationState{
            return Success(
                email = email,
                code = "",
                isCodeValid = true,
                isResendAllowed = false,
                resentTimer = -1,
            )
        }
    }
}