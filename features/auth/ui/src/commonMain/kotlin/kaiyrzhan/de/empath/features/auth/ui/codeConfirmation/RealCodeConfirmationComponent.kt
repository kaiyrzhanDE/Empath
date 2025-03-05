package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.flow.timerFlow
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal class RealCodeConfirmationComponent(
    componentContext: ComponentContext,
    private val email: String,
    private val onCodeConfirm: (email: String) -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, CodeConfirmationComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    override val state = MutableStateFlow<CodeConfirmationState>(
        CodeConfirmationState.Success(
            email = email,
            isResendAllowed = false,
            resentTimer = -1,
        )
    )

    init {
        startTimer()
    }

    override fun onEvent(event: CodeConfirmationEvent) {
        logger.d(this::class.simpleName.toString(), event.toString())
        when (event) {
            is CodeConfirmationEvent.CodeChange -> changeCode(event.code)
            is CodeConfirmationEvent.ResendClick -> resendCode()
            is CodeConfirmationEvent.CodeCheck -> checkCode()
            is CodeConfirmationEvent.BackClick -> backClick()
        }
    }

    private fun checkCode(){
        val currentState = state.value as? CodeConfirmationState.Success ?: return
        coroutineScope.launch {
            withContext(appDispatchers.io) {
                delay(500) //TODO("Request simulating, onSuccess")
            }
        }
    }

    private fun backClick() {
        //TODO("SHOWING DIALOG WITH TEXT: Are you sure you want to abort the verification process?")
        onBackClick()
    }

    private fun resendCode() {
        coroutineScope.launch {
            withContext(appDispatchers.io) {
                delay(500) //TODO("Request simulating, onSuccess")
            }
            startTimer()
        }
    }//TODO("Need implementation)

    private fun changeCode(code: String) {
        state.update { currentState ->
            if (currentState !is CodeConfirmationState.Success) return@update currentState

            currentState.copy(
                code = code,
            )
        }
    }

    private fun startTimer() {
        coroutineScope.launch {
            timerFlow(10).collect { remainingSeconds ->
                state.update { currentState ->
                    if (currentState !is CodeConfirmationState.Success) return@update currentState

                    currentState.copy(
                        resentTimer = remainingSeconds,
                        isResendAllowed = remainingSeconds == 0,
                    )
                }
            }
        }
    }
}