package kaiyrzhan.de.empath.features.auth.ui.emailVerification.model

public sealed class EmailVerificationEvent {
    internal class EmailChange(val email: String): EmailVerificationEvent()
    internal data object SendCodeClick: EmailVerificationEvent()
    internal data object BackClick: EmailVerificationEvent()
}