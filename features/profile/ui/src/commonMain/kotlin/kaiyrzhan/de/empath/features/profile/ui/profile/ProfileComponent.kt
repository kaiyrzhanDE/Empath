package kaiyrzhan.de.empath.features.profile.ui.profile

import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.flow.StateFlow

internal interface ProfileComponent {

    val state: StateFlow<ProfileState>

    fun onEvent(event: ProfileEvent)

}