package kaiyrzhan.de.empath.core.ui.dialog.date_picker.model

import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api

public enum class PickerType {
    PICKER,
    INPUT;

    @OptIn(ExperimentalMaterial3Api::class)
    public fun toMode(): DisplayMode {
        return when (this) {
            PICKER -> DisplayMode.Picker
            INPUT -> DisplayMode.Input
        }
    }
}