package kaiyrzhan.de.empath.features.auth.ui.signUp

import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface SignUpComponent {

    public val state: StateFlow<SignUpState>

    public val action: Flow<SignUpAction>

    public fun onEvent(event: SignUpEvent)

}