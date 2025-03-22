package kaiyrzhan.de.empath.features.profile.ui.profile

import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeProfileComponent : ProfileComponent {
    override val state = MutableStateFlow<ProfileState>(ProfileState.default())

    override fun onEvent(event: ProfileEvent) = Unit
}