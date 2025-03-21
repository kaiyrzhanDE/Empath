package kaiyrzhan.de.empath.features.auth.ui.signUp.model

internal sealed class SignUpAction {
    class ShowSnackbar(val message: String) : SignUpAction()
}