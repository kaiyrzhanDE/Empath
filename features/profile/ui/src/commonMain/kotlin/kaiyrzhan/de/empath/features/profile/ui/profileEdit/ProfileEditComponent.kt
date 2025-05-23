package kaiyrzhan.de.empath.features.profile.ui.profileEdit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditAction
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ProfileEditComponent: BackHandlerOwner {

    val state: StateFlow<ProfileEditState>

    val action: Flow<ProfileEditAction>

    val datePicker: Value<ChildSlot<*, DatePickerComponent>>

    fun onEvent(event: ProfileEditEvent)

}