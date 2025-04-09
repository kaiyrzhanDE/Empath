package kaiyrzhan.de.empath.features.profile.ui.profile

import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileAction
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ProfileComponent {

    val state: StateFlow<ProfileState>

    val action: Flow<ProfileAction>

    fun onEvent(event: ProfileEvent)

}