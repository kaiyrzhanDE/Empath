package kaiyrzhan.de.empath.features.profile.ui.profile_edit.model

internal sealed interface ProfileEditAction {
    class ShowSnackbar(val message: String): ProfileEditAction
}