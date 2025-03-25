package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PasswordRecoveryComponent : BackHandlerOwner {

    val state: StateFlow<PasswordRecoveryState>

    val action: Flow<PasswordRecoveryAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    fun onEvent(event: PasswordRecoveryEvent)

}