package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

public sealed class EmailVerificationAction {
    public data class ShowSnackbar(val message: String): EmailVerificationAction()
}