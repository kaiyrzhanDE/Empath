package kaiyrzhan.de.empath.features.profile.ui.profile

import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileAction
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeProfileComponent : ProfileComponent {
    override val state = MutableStateFlow<ProfileState>(ProfileState.default())
    override val action: Flow<ProfileAction> = emptyFlow()
    override fun onEvent(event: ProfileEvent) = Unit
}