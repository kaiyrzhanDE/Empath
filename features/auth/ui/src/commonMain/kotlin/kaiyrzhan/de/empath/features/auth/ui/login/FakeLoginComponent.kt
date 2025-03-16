package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

@Suppress("unused")
internal class FakeLoginComponent : LoginComponent {
    override val state = MutableStateFlow<LoginState>(LoginState.Success())

    override val action: Flow<LoginAction> = emptyFlow()

    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> =
        MutableValue(ChildSlot<Any, MessageDialogComponent>())

    override fun onEvent(event: LoginEvent) = Unit
}
