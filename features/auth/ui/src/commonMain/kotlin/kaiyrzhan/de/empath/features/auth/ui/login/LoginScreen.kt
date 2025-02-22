package kaiyrzhan.de.empath.features.auth.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun LoginScreen(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    LoginScreen(
        modifier = modifier,
        onLoginClick = {},
        onSignUpClick = {},
        onResetPasswordClick = {},
        onGoogleAuthClick = {},
    )
}


@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onGoogleAuthClick: () -> Unit,
) {

}
