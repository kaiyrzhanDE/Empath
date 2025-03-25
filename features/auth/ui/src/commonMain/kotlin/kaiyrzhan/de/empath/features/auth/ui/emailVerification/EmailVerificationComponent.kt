package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationAction
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface EmailVerificationComponent {

    val state: StateFlow<EmailVerificationState>

    val action: Flow<EmailVerificationAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    fun onEvent(event: EmailVerificationEvent)

}