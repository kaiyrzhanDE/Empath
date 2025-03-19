package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import empath.core.uikit.generated.resources.*
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RealLoginComponent(
    componentContext: ComponentContext,
    private val onLoginClick: () -> Unit,
    private val onSignUpClick: () -> Unit,
    private val onPasswordResetClick: () -> Unit,
) : ComponentContext by componentContext, LoginComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    private val logInUseCase: LogInUseCase by inject()

    override val state = MutableStateFlow<LoginState>(LoginState.Success())

    private val _action = Channel<LoginAction>(capacity = Channel.BUFFERED)
    override val action: Flow<LoginAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    override fun onEvent(event: LoginEvent) {
        logger.d(this::class.simpleName.toString(), event.toString())
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

    private fun createMessageDialog(
        state: MessageDialogState,
        childComponentContext: ComponentContext,
    ): MessageDialogComponent {
        return RealMessageDialogComponent(
            componentContext = childComponentContext,
            dialogState = state,
        )
    }

    private fun showMessageDialog(
        title: String,
        description: String,
        dismissActionConfig: MessageActionConfig,
        confirmActionConfig: MessageActionConfig? = null,
        onDismissClick: (() -> Unit)? = null,
        onConfirmClick: (() -> Unit)? = null,
    ) {
        messageDialogNavigation.activate(
            configuration = MessageDialogState(
                title = title,
                description = description,
                dismissActionConfig = dismissActionConfig,
                onDismissClick = {
                    messageDialogNavigation
                        .dismiss()
                        .also { onDismissClick?.invoke() }
                },
                confirmActionConfig = confirmActionConfig,
                onConfirmClick = onConfirmClick,
            ),
        )
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            if (currentState !is LoginState.Success) return@update currentState
            currentState.copy(
                password = password,
                isPasswordValid = true,
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            if (currentState !is LoginState.Success) return@update currentState
            currentState.copy(
                email = email,
                isEmailValid = true,
            )
        }
    }

    private fun logIn() {
        val currentState = state.value
        if (currentState !is LoginState.Success) return
        state.update { LoginState.Loading }
        coroutineScope.launch {
            logInUseCase(
                email = currentState.email,
                password = currentState.password,
            ).onSuccess {
                state.update { currentState }
                onLoginClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is LogInUseCaseError.InvalidEmailOrPassword -> {
                        state.update {
                            currentState.copy(
                                isEmailValid = false,
                                isPasswordValid = false
                            )
                        }
                        _action.send(LoginAction.ShowSnackbar(getString(FeatureRes.string.invalid_email_or_password)))
                    }

                    is LogInUseCaseError.TooManyLoginAttempts -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.too_many_login_attempts_title),
                            description = getString(FeatureRes.string.too_many_login_attempts_description),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }

                    is Result.Error.UnknownError -> {
                        showMessageDialog(
                            title = getString(CoreRes.string.unknown_error),
                            description = error.throwable.message.orEmpty(),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }

                    is Result.Error.UnknownRemoteError -> {
                        showMessageDialog(
                            title = getString(CoreRes.string.unknown_remote_error),
                            description = error.toString(),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun signUp() = onSignUpClick()

    private fun resetPassword() = onPasswordResetClick()

    private fun authWithFacebook() = Unit //TODO("Need implementation)

    private fun authWithGoogle() = Unit //TODO("Need implementation)
}
