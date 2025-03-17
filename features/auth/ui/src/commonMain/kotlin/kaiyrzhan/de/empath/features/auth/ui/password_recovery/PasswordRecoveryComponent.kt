package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface PasswordRecoveryComponent: BackHandlerOwner {

    public val state: StateFlow<PasswordRecoveryState>

    public val action: Flow<PasswordRecoveryAction>

    public val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    public fun onEvent(event: PasswordRecoveryEvent)

}