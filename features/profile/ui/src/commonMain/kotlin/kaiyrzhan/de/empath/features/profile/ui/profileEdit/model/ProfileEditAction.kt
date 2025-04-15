package kaiyrzhan.de.empath.features.profile.ui.profileEdit.model

internal sealed interface ProfileEditAction {
    class ShowSnackbar(val message: String): ProfileEditAction
}