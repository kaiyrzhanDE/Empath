package kaiyrzhan.de.empath.features.auth.ui.login

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface LoginComponent {

    public val state: StateFlow<LoginState>

    public val action: Flow<LoginAction>

    public val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    public fun onEvent(event: LoginEvent)
}


