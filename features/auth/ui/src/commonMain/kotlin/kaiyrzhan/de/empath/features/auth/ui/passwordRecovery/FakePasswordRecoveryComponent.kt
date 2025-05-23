package kaiyrzhan.de.empath.features.auth.ui.passwordRecovery

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.passwordRecovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.passwordRecovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.passwordRecovery.model.PasswordRecoveryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakePasswordRecoveryComponent : PasswordRecoveryComponent {
    override val state = MutableStateFlow(
        PasswordRecoveryState.Success(
            email = "sansyzbaev.de@gmail.com",
            isPasswordValid = false,
            password = "12345",
            isPasswordVisible = false,
            repeatedPassword = "12345",
            isRepeatedPasswordValid = false,
            isRepeatedPasswordVisible = false,
            arePasswordsMatching = true,
        )
    )

    override val action: Flow<PasswordRecoveryAction> = emptyFlow()

    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> =
        MutableValue(ChildSlot<Any, MessageDialogComponent>())

    override val backHandler: BackHandler = object : BackHandler {
        override fun isRegistered(callback: BackCallback): Boolean = false
        override fun register(callback: BackCallback) = Unit
        override fun unregister(callback: BackCallback) = Unit
    }

    override fun onEvent(event: PasswordRecoveryEvent) = Unit
}