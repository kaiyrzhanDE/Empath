package kaiyrzhan.de.empath.features.profile.ui.profile.model

internal sealed interface ProfileEvent {
    data object LogOut: ProfileEvent
    data object Reload: ProfileEvent
    data object UserPageClick: ProfileEvent
    data object EditProfileClick: ProfileEvent
}