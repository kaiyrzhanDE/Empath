package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kotlinx.coroutines.flow.StateFlow

public interface CodeConfirmationComponent {

    public val state: StateFlow<CodeConfirmationState>

    public fun onEvent(event: CodeConfirmationEvent)

}