package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

internal sealed interface EmailVerificationAction {
    class ShowSnackbar(val message: String) : EmailVerificationAction
}