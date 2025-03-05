package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeCodeConfirmationComponent : CodeConfirmationComponent {
    override val state = MutableStateFlow(
        CodeConfirmationState.Success(
            code = "12",
            email = "empath.app@gmail.com",
            isResendAllowed = true,
            resentTimer = 39,
        )
    )

    override fun onEvent(event: CodeConfirmationEvent) = Unit
}