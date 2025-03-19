package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.*
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.usecase.ResetPasswordUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.ResetPasswordUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
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
import kotlin.getValue

internal class RealPasswordRecoveryComponent(
    componentContext: ComponentContext,
    email: String,
    private val onPasswordReset: () -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, PasswordRecoveryComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    private val resetPasswordUseCase: ResetPasswordUseCase by inject()

    override val state = MutableStateFlow<PasswordRecoveryState>(
        PasswordRecoveryState.Success(
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


    private fun backClick() {
        coroutineScope.launch {
            showMessageDialog(
                title = getString(FeatureRes.string.abort_password_recovery_title),
                description = getString(FeatureRes.string.abort_password_recovery_description),
                dismissActionConfig = MessageActionConfig(
                    text = getString(CoreRes.string.close),
                    isPrimary = true,
                ),
                confirmActionConfig = MessageActionConfig(
                    text = getString(CoreRes.string.okay),
                    isPrimary = false,
                ),
                onConfirmClick = onBackClick,
            )
        }
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
                isPasswordValid = true,
                arePasswordsMatching = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            if (currentState !is PasswordRecoveryState.Success) return@update currentState

            currentState.copy(
                repeatedPassword = password,
                isRepeatedPasswordValid = true,
                arePasswordsMatching = true,
            )
        }
    }

    private fun resetPassword() {
        val currentState = state.value as? PasswordRecoveryState.Success ?: return
        state.update { PasswordRecoveryState.Loading }
        coroutineScope.launch {
            if (currentState.password != currentState.repeatedPassword) {
                state.update { currentState.copy(arePasswordsMatching = false) }
                _action.send(PasswordRecoveryAction.ShowSnackbar(getString(FeatureRes.string.password_dont_match_error)))
                return@launch
            }

            resetPasswordUseCase(
                email = currentState.email,
                password = currentState.password,
            ).onSuccess {
                state.update { currentState }
                onPasswordReset()
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
                        _action.send(PasswordRecoveryAction.ShowSnackbar(getString(FeatureRes.string.invalid_email_or_password)))
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
                                isPrimary = false,
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

}