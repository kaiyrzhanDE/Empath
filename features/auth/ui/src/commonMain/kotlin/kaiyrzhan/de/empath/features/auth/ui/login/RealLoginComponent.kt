package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

internal class RealLoginComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, LoginComponent, KoinComponent {

    override val state = MutableStateFlow<LoginState>(LoginState.Initial)

    override fun onEvent(event: LoginEvent) {
        when (event) {
            else -> TODO()
        }
    }
}
