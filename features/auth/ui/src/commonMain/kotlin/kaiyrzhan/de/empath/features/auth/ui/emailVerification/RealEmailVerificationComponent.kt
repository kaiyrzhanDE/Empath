package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.features.auth.ui.generated.resources.*
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCaseError
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationAction
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kaiyrzhan.de.empath.features.auth.ui.root.model.VerificationType
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

internal class RealEmailVerificationComponent(
    componentContext: ComponentContext,
    email: String,
    private val verificationType: VerificationType,
    private val onBackClick: () -> Unit,
    private val onSendSignUpCodeClick: (email: String) -> Unit,
    private val onSendResetPasswordCodeClick: (email: String) -> Unit,
    private val onSignUpClick: (email: String) -> Unit,
    private val onResetPasswordClick: (email: String) -> Unit,
) : BaseComponent(componentContext), EmailVerificationComponent {

    private val sendSignUpCodeUseCase: SendSignUpCodeUseCase by inject()
    private val sendResetPasswordCodeUseCase: SendResetPasswordCodeUseCase by inject()

    override val state = MutableStateFlow<EmailVerificationState>(
        EmailVerificationState.default(
            email = email,
        )
    )

    private val _action = Channel<EmailVerificationAction>(capacity = Channel.BUFFERED)
    override val action: Flow<EmailVerificationAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    override fun onEvent(event: EmailVerificationEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is EmailVerificationEvent.EmailChange -> changeEmail(event.email)
            is EmailVerificationEvent.SendCodeClick -> sendCode()
            is EmailVerificationEvent.BackClick -> onBackClick()
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

    private fun sendCode() {
        when (verificationType) {
            VerificationType.SIGN_UP -> sendSignUpCode()
            VerificationType.RESET_PASSWORD -> sendResetPasswordCode()
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            check(currentState is EmailVerificationState.Success)
            currentState.copy(
                email = email,
                isEmailValid = true,
            )
        }
    }

    private fun sendResetPasswordCode() {
        val currentState = state.value
        check(currentState is EmailVerificationState.Success)
        state.update { EmailVerificationState.Loading }
        coroutineScope.launch {
            sendResetPasswordCodeUseCase(currentState.email).onSuccess {
                onSendResetPasswordCodeClick(currentState.email)
                delay(1000)
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SendResetPasswordCodeUseCaseError.InvalidEmail -> {
                        state.update { currentState.copy(isEmailValid = false) }
                        _action.send(EmailVerificationAction.ShowSnackbar(getString(FeatureRes.string.invalid_email)))
                    }

                    is SendResetPasswordCodeUseCaseError.TooManyResetPasswordAttempts -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.too_many_password_recovery_attempts_title),
                            description = getString(FeatureRes.string.too_many_password_recovery_attempts_description),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }

                    is SendResetPasswordCodeUseCaseError.EmailIsNotRegistered -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.email_is_not_registered_title),
                            description = getString(FeatureRes.string.email_is_not_registered_description),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(FeatureRes.string.use_different_email),
                            ),
                            confirmActionConfig = MessageActionConfig(
                                text = getString(FeatureRes.string.sign_up),
                                isPrimary = true,
                            ),
                            onDismissClick = {
                                state.update { currentState.copy(email = "", isEmailValid = true) }
                            },
                            onConfirmClick = {
                                onSignUpClick(currentState.email)
                            },
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
                                isPrimary = false,
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun sendSignUpCode() {
        val currentState = state.value
        check(currentState is EmailVerificationState.Success)
        state.update { EmailVerificationState.Loading }
        coroutineScope.launch {
            sendSignUpCodeUseCase(currentState.email).onSuccess {
                onSendSignUpCodeClick(currentState.email)
                delay(1000)
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SendSignUpCodeUseCaseError.InvalidEmail -> {
                        state.update { currentState.copy(isEmailValid = false) }
                        _action.send(EmailVerificationAction.ShowSnackbar(getString(FeatureRes.string.invalid_email)))
                    }

                    is SendSignUpCodeUseCaseError.TooManySignUpAttempts -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.too_many_sign_up_attempts_title),
                            description = getString(FeatureRes.string.too_many_sign_up_attempts_description),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }

                    is SendSignUpCodeUseCaseError.EmailAlreadyRegistered -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.email_already_registered_title),
                            description = getString(FeatureRes.string.email_already_registered_description),
                            dismissActionConfig = MessageActionConfig(
                                text = getString(FeatureRes.string.use_different_email),
                            ),
                            confirmActionConfig = MessageActionConfig(
                                text = getString(FeatureRes.string.reset_password),
                                isPrimary = true,
                            ),
                            onDismissClick = {
                                state.update { currentState.copy(email = "", isEmailValid = true) }
                            },
                            onConfirmClick = {
                                onResetPasswordClick(currentState.email)
                            },
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