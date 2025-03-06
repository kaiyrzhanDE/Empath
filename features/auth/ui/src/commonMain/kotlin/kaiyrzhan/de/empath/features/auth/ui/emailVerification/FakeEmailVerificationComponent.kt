package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeEmailVerificationComponent : EmailVerificationComponent {
    override val state = MutableStateFlow(EmailVerificationState.Success(""))
    override fun onEvent(event: EmailVerificationEvent) = Unit
}