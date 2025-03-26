package kaiyrzhan.de.empath.features.auth.ui.signUp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.usecase.SignUpUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SignUpUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject
import kotlin.getValue

internal class RealSignUpComponent(
    componentContext: ComponentContext,
    email: String,
    private val onBackClick: () -> Unit,
    private val onSignUpClick: () -> Unit,
) : BaseComponent(componentContext), SignUpComponent {

    private val signUpUseCase: SignUpUseCase by inject()

    override val state = MutableStateFlow<SignUpState>(
        SignUpState.default(
            email = email,
        )
    )

    private val _action: Channel<SignUpAction> = Channel(capacity = Channel.BUFFERED)
    override val action: Flow<SignUpAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    override fun onEvent(event: SignUpEvent) {
        logger.d(this.className(), "Event: $event")
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

    private fun backClick() {
        coroutineScope.launch {
            showMessageDialog(
                title = getString(Res.string.abort_registration_title),
                description = getString(Res.string.abort_registration_description),
                dismissActionConfig = DialogActionConfig(
                    text = getString(Res.string.close),
                    isPrimary = true,
                ),
                confirmActionConfig = DialogActionConfig(
                    text = getString(Res.string.okay),
                    isPrimary = false,
                ),
                onConfirmClick = onBackClick,
            )
        }
    }

    private fun showPassword() {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                isPasswordVisible = currentState.isPasswordVisible.not(),
            )
        }
    }

    private fun showRepeatedPassword() {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                isRepeatedPasswordVisible = currentState.isRepeatedPasswordVisible.not(),
            )
        }
    }

    private fun changeNickname(nickname: String) {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                nickname = nickname,
            )
        }
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                password = password,
                arePasswordsMatching = true,
                isPasswordValid = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                repeatedPassword = password,
                arePasswordsMatching = true,
                isRepeatedPasswordValid = true,
            )
        }
    }

    private fun acceptUserAgreement() {
        state.update { currentState ->
            check(currentState is SignUpState.Success)
            currentState.copy(
                isUserAgreementAccepted = currentState.isUserAgreementAccepted.not(),
            )
        }
    }

    private fun signUp() {
        val currentState = state.value
        check(currentState is SignUpState.Success)
        state.update { SignUpState.Loading }
        coroutineScope.launch {
            if (currentState.password != currentState.repeatedPassword) {
                state.update { currentState.copy(arePasswordsMatching = false) }
                _action.send(SignUpAction.ShowSnackbar(getString(Res.string.password_dont_match_error)))
                return@launch
            }

            signUpUseCase(
                email = currentState.email,
                password = currentState.password,
                nickname = currentState.nickname,
            ).onSuccess {
                onSignUpClick()
                delay(1000)
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SignUpUseCaseError.InvalidEmailOrPassword -> {
                        state.update {
                            currentState.copy(
                                isPasswordValid = false,
                                isRepeatedPasswordValid = false,
                            )
                        }
                        _action.send(SignUpAction.ShowSnackbar(getString(Res.string.invalid_email_or_password)))
                    }

                    is Result.Error.UnknownError -> {
                        showMessageDialog(
                            title = getString(Res.string.unknown_error),
                            description = error.throwable.message.orEmpty(),
                            dismissActionConfig = DialogActionConfig(
                                text = getString(Res.string.close),
                            ),
                        )
                    }

                    is Result.Error.UnknownRemoteError -> {
                        showMessageDialog(
                            title = getString(Res.string.unknown_remote_error),
                            description = error.toString(),
                            dismissActionConfig = DialogActionConfig(
                                isPrimary = false,
                                text = getString(Res.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

}