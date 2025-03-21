package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

internal sealed interface EmailVerificationEvent {
    data class EmailChange(val email: String) : EmailVerificationEvent
    data object SendCodeClick : EmailVerificationEvent
    data object BackClick : EmailVerificationEvent
}