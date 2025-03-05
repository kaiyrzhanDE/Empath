package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal class RealEmailVerificationComponent(
    componentContext: ComponentContext,
    private val onSendCodeClick: (email: String) -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, EmailVerificationComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    override val state = MutableStateFlow<EmailVerificationState>(EmailVerificationState.Success())

    override fun onEvent(event: EmailVerificationEvent) {
        logger.d(this::class.simpleName.toString(), event.toString())
        when (event) {
            is EmailVerificationEvent.EmailChange -> changeEmail(event.email)
            is EmailVerificationEvent.SendCodeClick -> sendCode()
            is EmailVerificationEvent.BackClick -> onBackClick()
        }
    }

    private fun sendCode() {
        val currentState = state.value as? EmailVerificationState.Success ?: return
        state.update { EmailVerificationState.Loading }
        coroutineScope.launch {
            withContext(appDispatchers.io) {
                delay(500) //TODO("Request simulating, onSuccess")
            }
            onSendCodeClick(currentState.email)
            delay(500)
            state.update { currentState }
        }
    }//TODO("Need implementation)

    private fun changeEmail(email: String) {
        state.update { currentState ->
            if (currentState !is EmailVerificationState.Success) return@update currentState

            currentState.copy(
                email = email,
            )
        }
    }
}