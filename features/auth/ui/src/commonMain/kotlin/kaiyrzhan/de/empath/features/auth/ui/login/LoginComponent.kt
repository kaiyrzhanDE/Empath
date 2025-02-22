package kaiyrzhan.de.empath.features.auth.ui.login

import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.StateFlow

public interface LoginComponent {
    public val state: StateFlow<LoginState>
    public fun onEvent(event: LoginEvent)
}


