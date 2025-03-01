package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RealLoginComponent(
    componentContext: ComponentContext,
    private val onLoginClick: () -> Unit,
) : ComponentContext by componentContext, LoginComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    override val state = MutableStateFlow<LoginState>(LoginState.Success())

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChange -> changeEmail(event.email)
            is LoginEvent.PasswordChange -> changePassword(event.password)
            is LoginEvent.SignUp -> signUp()
            is LoginEvent.ResetPassword -> resetPassword()
            is LoginEvent.LogIn -> logIn()
            is LoginEvent.FacebookAuthClick -> authWithFacebook()
            is LoginEvent.GoogleAuthClick -> authWithGoogle()
        }
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            logger.d("veildc", "changePassword: $currentState")
            if (currentState !is LoginState.Success) return@update currentState
            logger.d("veildc", "changePassword 1: $currentState")
            currentState.copy(password = password)
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            logger.d("veildc", "changeEmail: $currentState")
            if (currentState !is LoginState.Success) return@update currentState
            logger.d("veildc", "changeEmail 1: $currentState")
            currentState.copy(email = email)
        }
    }

    private fun logIn() {
        coroutineScope.launch {
            withContext(appDispatchers.io) {
                delay(2000)
            }
            onLoginClick()
        }
    } //TODO("Need implementation)

    private fun signUp() = Unit //TODO("Need implementation)
    private fun resetPassword() = Unit //TODO("Need implementation)
    private fun authWithFacebook() = Unit //TODO("Need implementation)
    private fun authWithGoogle() = Unit //TODO("Need implementation)


}
