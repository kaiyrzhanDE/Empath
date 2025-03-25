package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface LoginComponent {

    val state: StateFlow<LoginState>

    val action: Flow<LoginAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    fun onEvent(event: LoginEvent)
}


