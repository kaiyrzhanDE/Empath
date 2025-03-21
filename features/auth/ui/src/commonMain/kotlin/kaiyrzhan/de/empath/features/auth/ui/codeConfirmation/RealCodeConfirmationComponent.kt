package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.*
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.flow.timerFlow
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendResetPasswordCodeUseCaseError
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SendSignUpCodeUseCaseError
import kaiyrzhan.de.empath.features.auth.domain.usecase.VerifyCodeUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.VerifyCodeUseCaseError
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationAction
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
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

internal class RealCodeConfirmationComponent(
    componentContext: ComponentContext,
    email: String,
    private val verificationType: VerificationType,
    private val onBackClick: () -> Unit,
    private val onSignUpCodeConfirm: (email: String) -> Unit,
    private val onResetPasswordCodeConfirm: (email: String) -> Unit,
) : BaseComponent(componentContext), CodeConfirmationComponent {

    private val verifyCodeUseCase: VerifyCodeUseCase by inject()
    private val sendSignUpCodeUseCase: SendSignUpCodeUseCase by inject()
    private val sendResetPasswordCodeUseCase: SendResetPasswordCodeUseCase by inject()

    override val state = MutableStateFlow<CodeConfirmationState>(
        CodeConfirmationState.default(
            email = email,
        )
    )

    private val _action = Channel<CodeConfirmationAction>(capacity = Channel.BUFFERED)
    override val action: Flow<CodeConfirmationAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    init {
        startTimer()
    }

    override fun onEvent(event: CodeConfirmationEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is CodeConfirmationEvent.CodeChange -> changeCode(event.code)
            is CodeConfirmationEvent.ResendClick -> resendCode()
            is CodeConfirmationEvent.CodeVerify -> verifyCode()
            is CodeConfirmationEvent.BackClick -> onBackClick()
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

    private fun changeCode(code: String) {
        state.update { currentState ->
            check(currentState is CodeConfirmationState.Success)
            currentState.copy(
                code = code,
                isCodeValid = true,
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

    private fun verifyCode() {
        val currentState = state.value
        check(currentState is CodeConfirmationState.Success)
        val email = currentState.email
        state.update { CodeConfirmationState.Loading }
        coroutineScope.launch {
            verifyCodeUseCase(
                email = email,
                code = currentState.code,
            ).onSuccess {
                when (verificationType) {
                    VerificationType.RESET_PASSWORD -> onResetPasswordCodeConfirm(email)
                    VerificationType.SIGN_UP -> onSignUpCodeConfirm(email)
                }
                delay(1000)
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is VerifyCodeUseCaseError.InvalidCode -> {
                        state.update { currentState.copy(isCodeValid = false) }
                        _action.send(CodeConfirmationAction.ShowSnackbar(getString(FeatureRes.string.invalid_code)))
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

    private fun resendCode() {
        when (verificationType) {
            VerificationType.SIGN_UP -> resendSignUpCode()
            VerificationType.RESET_PASSWORD -> resendResetPasswordCode()
        }
    }

    private fun resendSignUpCode() {
        val currentState = state.value
        check(currentState is CodeConfirmationState.Success)
        state.update { CodeConfirmationState.Loading }
        coroutineScope.launch {
            sendSignUpCodeUseCase(email = currentState.email).onSuccess {
                showMessageDialog(
                    title = getString(FeatureRes.string.code_resent_title),
                    description = getString(FeatureRes.string.code_resent_description),
                    dismissActionConfig = MessageActionConfig(
                        text = getString(CoreRes.string.close),
                    ),
                )
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SendSignUpCodeUseCaseError.TooManySignUpAttempts -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.too_many_sign_up_attempts_title),
                            description = getString(FeatureRes.string.too_many_sign_up_attempts_description),
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
                                isPrimary = false,
                                text = getString(CoreRes.string.close),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun resendResetPasswordCode() {
        val currentState = state.value
        check(currentState is CodeConfirmationState.Success)
        state.update { CodeConfirmationState.Loading }
        coroutineScope.launch {
            sendResetPasswordCodeUseCase(email = currentState.email).onSuccess {
                showMessageDialog(
                    title = getString(FeatureRes.string.code_resent_title),
                    description = getString(FeatureRes.string.code_resent_description),
                    dismissActionConfig = MessageActionConfig(
                        text = getString(CoreRes.string.close),
                    ),
                )
                state.update { currentState }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SendResetPasswordCodeUseCaseError.TooManyResetPasswordAttempts -> {
                        showMessageDialog(
                            title = getString(FeatureRes.string.too_many_password_recovery_attempts_title),
                            description = getString(FeatureRes.string.too_many_password_recovery_attempts_description),
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