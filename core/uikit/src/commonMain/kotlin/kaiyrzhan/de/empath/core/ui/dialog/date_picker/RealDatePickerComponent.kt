package kaiyrzhan.de.empath.core.ui.dialog.date_picker

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogEvent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

public class RealDatePickerComponent(
    componentContext: ComponentContext,
    private val dialogState: DatePickerDialogState,
) : BaseComponent(componentContext), DatePickerComponent {

    override val state: MutableStateFlow<DatePickerDialogState> = MutableStateFlow(dialogState)

    override fun onEvent(event: DatePickerDialogEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is DatePickerDialogEvent.DismissClick -> dialogState.onDismissClick()
            is DatePickerDialogEvent.ConfirmClick -> confirm()
            is DatePickerDialogEvent.DateTimeSelect -> selectDateTime(event.selectedTimeMillis)
        }
    }

    private fun confirm() {
        val currentState = state.value
        currentState.onConfirmClick(currentState.selectedDate)
    }

    private fun selectDateTime(selectedTimeMillis: Long) {
        state.update { currentState ->
            currentState.copy(
                selectedDate = selectedTimeMillis.toLocalDateTime(),
            )
        }
    }
}