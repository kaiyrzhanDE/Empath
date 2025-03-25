package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationAction
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeCodeConfirmationComponent : CodeConfirmationComponent {
    override val state = MutableStateFlow(
        CodeConfirmationState.Success(
            code = "12",
            isCodeValid = false,
            email = "empath.app@gmail.com",
            isResendAllowed = true,
            resentTimer = 39,
        )
    )

    override val action: Flow<CodeConfirmationAction> = emptyFlow()

    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> =
        MutableValue(ChildSlot<Any, MessageDialogComponent>())

    override fun onEvent(event: CodeConfirmationEvent) = Unit
}