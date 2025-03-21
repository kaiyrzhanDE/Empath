package kaiyrzhan.de.empath.features.auth.ui.login.model

internal sealed interface LoginAction {
    class ShowSnackbar(val message: String) : LoginAction
}