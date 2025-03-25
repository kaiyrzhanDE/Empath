package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationAction
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

@Suppress("unused")
internal class FakeEmailVerificationComponent : EmailVerificationComponent {
    override val state = MutableStateFlow(
        EmailVerificationState.Success(
            email = "sansyzbaev.de@gmail.com",
            isEmailValid = false,
        )
    )

    override val action: Flow<EmailVerificationAction> = emptyFlow()

    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> =
        MutableValue(ChildSlot<Any, MessageDialogComponent>())

    override fun onEvent(event: EmailVerificationEvent) = Unit
}