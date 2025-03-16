package kaiyrzhan.de.empath.features.auth.ui.login.model

public sealed class LoginAction {
    internal class ShowSnackbar(val message: String) : LoginAction()
}