package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.*
import empath.core.uikit.generated.resources.Res
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.LogInUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject

internal class RealLoginComponent(
    componentContext: ComponentContext,
    private val onLoginClick: () -> Unit,
    private val onSignUpClick: () -> Unit,
    private val onPasswordResetClick: () -> Unit,
) : BaseComponent(componentContext), LoginComponent {

    private val logInUseCase: LogInUseCase by inject()

    override val state = MutableStateFlow<LoginState>(
        LoginState.default()
    )

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
        logger.d(this.className(), "Event: $event")
        when (event) {
            is LoginEvent.EmailChange -> changeEmail(event.email)
            is LoginEvent.PasswordChange -> changePassword(event.password)
            is LoginEvent.SignUp -> onSignUpClick()
            is LoginEvent.ResetPassword -> onPasswordResetClick()
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
            messageDialogState = state,
        )
    }

    private fun showMessageDialog(
        title: String,
        description: String,
        dismissActionConfig: DialogActionConfig,
        confirmActionConfig: DialogActionConfig? = null,
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
            check(currentState is LoginState.Success)
            currentState.copy(
                password = password,
                isPasswordValid = true,
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            check(currentState is LoginState.Success)
            currentState.copy(
                email = email,
                isEmailValid = true,
            )
        }
    }


    private fun logIn() {
        val currentState = state.value
        check(currentState is LoginState.Success)
        state.update { LoginState.Loading }
        coroutineScope.launch {
            logInUseCase(
                email = currentState.email,
                password = currentState.password,
            ).onSuccess {
                onLoginClick()
                state.update { currentState }
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
                        _action.send(LoginAction.ShowSnackbar(getString(Res.string.invalid_email_or_password)))
                    }

                    is LogInUseCaseError.TooManyLoginAttempts -> {
                        showMessageDialog(
                            title = getString(Res.string.too_many_login_attempts_title),
                            description = getString(Res.string.too_many_login_attempts_description),
                            dismissActionConfig = DialogActionConfig(
                                text = getString(Res.string.close),
                            ),
                        )
                    }

                    is Result.Error.DefaultError -> {
                        showMessageDialog(
                            title = getString(Res.string.unknown_error),
                            description = error.toString(),
                            dismissActionConfig = DialogActionConfig(
                                text = getString(Res.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun authWithFacebook() = Unit //TODO("Need implementation google auth)

    private fun authWithGoogle() = Unit //TODO("Need implementation facebook auth)
}
