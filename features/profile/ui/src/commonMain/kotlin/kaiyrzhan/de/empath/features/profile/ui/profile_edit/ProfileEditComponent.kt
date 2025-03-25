package kaiyrzhan.de.empath.features.profile.ui.profile_edit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditAction
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ProfileEditComponent {

    val state: StateFlow<ProfileEditState>

    val action: Flow<ProfileEditAction>

    val datePicker: Value<ChildSlot<*, DatePickerComponent>>

    fun onEvent(event: ProfileEditEvent)

}