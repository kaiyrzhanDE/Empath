package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.features.auth.ui.generated.resources.password_dont_match_error
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal class RealPasswordRecoveryComponent(
    componentContext: ComponentContext,
    private val email: String,
    private val onPasswordReset: () -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, PasswordRecoveryComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    override val state = MutableStateFlow<PasswordRecoveryState>(
        PasswordRecoveryState.Success(
            email = email,
        )
    )

    private val _action: Channel<PasswordRecoveryAction> = Channel(capacity = Channel.BUFFERED)
    override val action: Flow<PasswordRecoveryAction> = _action.receiveAsFlow()

    override fun onEvent(event: PasswordRecoveryEvent) {
        logger.d(this::class.simpleName.toString(), event.toString())
        when (event) {
            is PasswordRecoveryEvent.BackClick -> backClick()
            is PasswordRecoveryEvent.PasswordChange -> changePassword(event.password)
            is PasswordRecoveryEvent.RepeatedPasswordChange -> changeRepeatedPassword(event.password)
            is PasswordRecoveryEvent.PasswordReset -> resetPassword()
            is PasswordRecoveryEvent.PasswordShow -> showPassword()
            is PasswordRecoveryEvent.RepeatedPasswordShow -> showRepeatedPassword()
        }
    }

    private fun backClick() {
        //TODO("SHOWING DIALOG WITH TEXT: Are you sure you want to abort the registration process?")
        onBackClick()
    }

    private fun showPassword() {
        state.update { currentState ->
            if (currentState !is PasswordRecoveryState.Success) return@update currentState

            currentState.copy(
                isPasswordVisible = currentState.isPasswordVisible.not(),
            )
        }
    }

    private fun showRepeatedPassword() {
        state.update { currentState ->
            if (currentState !is PasswordRecoveryState.Success) return@update currentState

            currentState.copy(
                isRepeatedPasswordVisible = currentState.isRepeatedPasswordVisible.not(),
            )
        }
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            if (currentState !is PasswordRecoveryState.Success) return@update currentState

            currentState.copy(
                password = password,
                arePasswordsMatching = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            if (currentState !is PasswordRecoveryState.Success) return@update currentState

            currentState.copy(
                repeatedPassword = password,
                arePasswordsMatching = true,
            )
        }
    }

    private fun resetPassword() {
        val currentState = state.value as? PasswordRecoveryState.Success ?: return
        coroutineScope.launch {
            if (currentState.equalsPasswords().not()) {
                state.update { currentState.copy(arePasswordsMatching = false) }
                _action.send(PasswordRecoveryAction.ShowSnackbar(getString(FeatureRes.string.password_dont_match_error)))
                return@launch
            }
            state.update { PasswordRecoveryState.Loading }
            withContext(appDispatchers.io) {
                delay(500) //TODO("Request simulating, onSuccess")
            }
            onPasswordReset()
            delay(500)
            state.update { currentState }
        }
    }//TODO("Need implementation)

}