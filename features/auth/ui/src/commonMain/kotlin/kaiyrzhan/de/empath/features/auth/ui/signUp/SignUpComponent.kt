package kaiyrzhan.de.empath.features.auth.ui.signUp

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface SignUpComponent : BackHandlerOwner {

    val state: StateFlow<SignUpState>

    val action: Flow<SignUpAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    fun onEvent(event: SignUpEvent)

}