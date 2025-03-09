package kaiyrzhan.de.empath.features.auth.ui.signUp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.features.auth.ui.generated.resources.password_dont_match_error
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
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

internal class RealSignUpComponent(
    componentContext: ComponentContext,
    private val email: String,
    private val onSignUpClick: () -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, SignUpComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    override val state = MutableStateFlow<SignUpState>(
        SignUpState.Success(
            email = email,
        )
    )

    private val _action: Channel<SignUpAction> = Channel(capacity = Channel.BUFFERED)
    override val action: Flow<SignUpAction> = _action.receiveAsFlow()

    override fun onEvent(event: SignUpEvent) {
        logger.d(this::class.simpleName.toString(), event.toString())
        when (event) {
            is SignUpEvent.BackClick -> backClick()
            is SignUpEvent.NicknameChange -> changeNickname(event.nickname)
            is SignUpEvent.PasswordChange -> changePassword(event.password)
            is SignUpEvent.RepeatedPasswordChange -> changeRepeatedPassword(event.password)
            is SignUpEvent.UserAgreementAccept -> acceptUserAgreement()
            is SignUpEvent.SignUp -> signUp()
            is SignUpEvent.UserAgreementClick -> TODO("Need implementation WebView or screen with user agreement")
            is SignUpEvent.PasswordShow -> showPassword()
            is SignUpEvent.RepeatedPasswordShow -> showRepeatedPassword()
        }
    }

    private fun backClick() {
        //TODO("SHOWING DIALOG WITH TEXT: Are you sure you want to abort the registration process?")
        onBackClick()
    }

    private fun showPassword() {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                isPasswordVisible = currentState.isPasswordVisible.not(),
            )
        }
    }

    private fun showRepeatedPassword() {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                isRepeatedPasswordVisible = currentState.isRepeatedPasswordVisible.not(),
            )
        }
    }

    private fun changeNickname(nickname: String) {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                nickname = nickname,
            )
        }
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                password = password,
                arePasswordsMatching = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                repeatedPassword = password,
                arePasswordsMatching = true,
            )
        }
    }

    private fun acceptUserAgreement() {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                isUserAgreementAccepted = currentState.isUserAgreementAccepted.not(),
            )
        }
    }

    private fun signUp() {
        val currentState = state.value as? SignUpState.Success ?: return
        coroutineScope.launch {
            if (currentState.equalsPasswords().not()) {
                state.update { currentState.copy(arePasswordsMatching = false) }
                _action.send(SignUpAction.ShowSnackbar(getString(FeatureRes.string.password_dont_match_error)))
                return@launch
            }
            state.update { SignUpState.Loading }
            withContext(appDispatchers.io) {
                delay(500) //TODO("Request simulating, onSuccess")
            }
            onSignUpClick()
            delay(500)
            state.update { currentState }
        }
    }//TODO("Need implementation)

}