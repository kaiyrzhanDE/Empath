package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.flow.StateFlow

public interface EmailVerificationComponent {

    public val state: StateFlow<EmailVerificationState>

    public fun onEvent(event: EmailVerificationEvent)

}