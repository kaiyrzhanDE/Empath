package kaiyrzhan.de.empath.features.auth.ui.login

import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeLoginComponent : LoginComponent {
    override val state = MutableStateFlow<LoginState>(LoginState.Success())
    override fun onEvent(event: LoginEvent) = Unit
}
