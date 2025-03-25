package kaiyrzhan.de.empath.features.auth.ui.signUp

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeSignUpComponent : SignUpComponent {
    override val state = MutableStateFlow(
        SignUpState.Success(
            email = "sansyzbaev.de@gmail.com",
            nickname = "veildc",
            password = "12345",
            isPasswordVisible = false,
            isPasswordValid = true,
            repeatedPassword = "12345",
            isRepeatedPasswordVisible = false,
            isRepeatedPasswordValid = true,
            isUserAgreementAccepted = true,
            arePasswordsMatching = true,
        )
    )

    override val action: Flow<SignUpAction> = emptyFlow()

    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> =
        MutableValue(ChildSlot<Any, MessageDialogComponent>())

    override val backHandler: BackHandler = object : BackHandler {
        override fun isRegistered(callback: BackCallback): Boolean = false
        override fun register(callback: BackCallback) = Unit
        override fun unregister(callback: BackCallback) = Unit
    }

    override fun onEvent(event: SignUpEvent) = Unit
}