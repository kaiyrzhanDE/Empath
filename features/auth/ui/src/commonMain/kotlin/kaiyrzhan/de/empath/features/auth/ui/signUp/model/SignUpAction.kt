package kaiyrzhan.de.empath.features.auth.ui.signUp.model

public sealed class SignUpAction {
    internal data class ShowSnackbar(val message: String): SignUpAction()
}