package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationAction
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface EmailVerificationComponent {

    public val state: StateFlow<EmailVerificationState>

    public val action: Flow<EmailVerificationAction>

    public val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    public fun onEvent(event: EmailVerificationEvent)

}