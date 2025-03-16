package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialogComponent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationAction
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface CodeConfirmationComponent {

    public val state: StateFlow<CodeConfirmationState>

    public val action: Flow<CodeConfirmationAction>

    public val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    public fun onEvent(event: CodeConfirmationEvent)

}