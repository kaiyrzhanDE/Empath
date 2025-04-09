package kaiyrzhan.de.empath.features.profile.ui.profile.model

internal sealed interface ProfileAction {
    class ShowSnackBar(val message: String) : ProfileAction
}