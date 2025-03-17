package kaiyrzhan.de.empath.features.auth.ui.signUp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.core.uikit.generated.resources.*
import empath.features.auth.ui.generated.resources.*
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageActionConfig
import kaiyrzhan.de.empath.core.ui.dialog.model.MessageDialogState
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.auth.domain.usecase.SignUpUseCase
import kaiyrzhan.de.empath.features.auth.domain.usecase.SignUpUseCaseError
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
    email: String,
    private val onSignUpClick: () -> Unit,
    private val onBackClick: () -> Unit,
) : ComponentContext by componentContext, SignUpComponent, KoinComponent {

    private val appDispatchers: AppDispatchers by inject()
    private val logger: BaseLogger by inject()
    private val coroutineScope = coroutineScope(appDispatchers.main + SupervisorJob())

    private val signUpUseCase: SignUpUseCase by inject()

    override val state = MutableStateFlow<SignUpState>(
        SignUpState.Success(
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
                title = getString(FeatureRes.string.abort_registration_title),
                description = getString(FeatureRes.string.abort_registration_description),
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
                isPasswordValid = true,
            )
        }
    }

    private fun changeRepeatedPassword(password: String) {
        state.update { currentState ->
            if (currentState !is SignUpState.Success) return@update currentState

            currentState.copy(
                repeatedPassword = password,
                arePasswordsMatching = true,
                isRepeatedPasswordValid = true,
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
        state.update { SignUpState.Loading }
        coroutineScope.launch {
            signUpUseCase(
                email = currentState.email,
                password = currentState.password,
                repeatedPassword = currentState.repeatedPassword,
                nickname = currentState.nickname,
            ).onSuccess {
                state.update { currentState }
                onSignUpClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is SignUpUseCaseError.PasswordsDontMatch -> {
                        state.update { currentState.copy(arePasswordsMatching = false) }
                        _action.send(SignUpAction.ShowSnackbar(getString(FeatureRes.string.password_dont_match_error)))
                    }

                    is SignUpUseCaseError.InvalidEmailOrPassword -> {
                        state.update {
                            currentState.copy(
                                isPasswordValid = false,
                                isRepeatedPasswordValid = false,
                            )
                        }
                        _action.send(SignUpAction.ShowSnackbar(getString(FeatureRes.string.invalid_email_or_password)))
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