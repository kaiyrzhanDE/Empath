package kaiyrzhan.de.empath.features.profile.ui.profile_edit.model

internal sealed class FieldState {
    object Loading : FieldState()
    object Unselected : FieldState()
    data class Selected(
        val text: String,
    ) : FieldState()
}