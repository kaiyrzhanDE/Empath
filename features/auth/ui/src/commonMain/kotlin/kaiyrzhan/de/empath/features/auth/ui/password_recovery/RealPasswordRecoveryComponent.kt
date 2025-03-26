package kaiyrzhan.de.empath.features.auth.ui.password_recovery

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
import kaiyrzhan.de.empath.features.auth.domain.usecase.ResetPasswordUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.ResetPasswordUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
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

internal class RealPasswordRecoveryComponent(
    componentContext: ComponentContext,
    email: String,
    private val onPasswordReset: () -> Unit,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), PasswordRecoveryComponent {

    private val resetPasswordUseCase: ResetPasswordUseCase by inject()

    override val state = MutableStateFlow<PasswordRecoveryState>(
        PasswordRecoveryState.default(
            email = email,
        )
    )

    private val _action: Channel<PasswordRecoveryAction> = Channel(capacity = Channel.BUFFERED)
    override val action: Flow<PasswordRecoveryAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    override fun onEvent(event: PasswordRecoveryEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PasswordRecoveryEvent.BackClick -> backClick()
            is PasswordRecoveryEvent.PasswordChange -> changePassword(event.password)
            is PasswordRecoveryEvent.RepeatedPasswordChange -> changeRepeatedPassword(event.password)
            is PasswordRecoveryEvent.PasswordReset -> resetPassword()
            is PasswordRecoveryEvent.PasswordShow -> showPassword()
            is PasswordRecoveryEvent.RepeatedPasswordShow -> showRepeatedPassword()
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
                title = getString(Res.string.abort_password_recovery_title),
                description = getString(Res.string.abort_password_recovery_description),
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
            check(currentState is PasswordRecoveryState.Success)
            currentState.copy(
                isPasswordVisible = currentState.isPasswordVisible.not(),
            )
        }
    }

    private fun showRepeatedPassword() {
        state.update { currentState ->
            check(currentState is PasswordRecoveryState.Success)
            currentState.copy(
                isRepeatedPasswordVisible = currentState.isRepeatedPasswordVisible.not(),
            )
        }
    }

    private fun changePassword(password: String) {
        state.update { currentState ->
            check(currentState is PasswordRecoveryState.Success)
            currentState.copy(
                password = password,
                isPasswordValid = true,
                arePasswordsMatching = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            check(currentState is PasswordRecoveryState.Success)
            currentState.copy(
                repeatedPassword = password,
                isRepeatedPasswordValid = true,
                arePasswordsMatching = true,
            )
        }
    }

    private fun resetPassword() {
        val currentState = state.value
        check(currentState is PasswordRecoveryState.Success)
        state.update { PasswordRecoveryState.Loading }
        coroutineScope.launch {
            if (currentState.password != currentState.repeatedPassword) {
                state.update { currentState.copy(arePasswordsMatching = false) }
                _action.send(PasswordRecoveryAction.ShowSnackbar(getString(Res.string.password_dont_match_error)))
                return@launch
            }

            resetPasswordUseCase(
                email = currentState.email,
                password = currentState.password,
            ).onSuccess {
                onPasswordReset()
                delay(1000)
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is ResetPasswordUseCaseError.InvalidPasswordOrEmail -> {
                        state.update {
                            currentState.copy(
                                isPasswordValid = false,
                                isRepeatedPasswordValid = false,
                            )
                        }
                        _action.send(PasswordRecoveryAction.ShowSnackbar(getString(Res.string.invalid_email_or_password)))
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