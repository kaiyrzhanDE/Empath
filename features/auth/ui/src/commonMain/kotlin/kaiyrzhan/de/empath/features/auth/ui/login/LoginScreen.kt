package kaiyrzhan.de.empath.features.auth.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.auth.ui.components.Logo
import kaiyrzhan.de.empath.features.auth.ui.login.components.SecondaryAuthButtons
import kaiyrzhan.de.empath.features.auth.ui.login.components.TitledDivider
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginAction
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun LoginScreen(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    val loginState = component.state.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    val messageDialogSlot by component.messageDialog.subscribeAsState()
    messageDialogSlot.child?.instance?.also { messageComponent ->
        MessageDialog(
            component = messageComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is LoginAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        loginState = loginState.value,
        onEvent = component::onEvent,
    )
}


@Composable
private fun LoginScreen(
    loginState: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    when (loginState) {
        is LoginState.Success -> {
            Column(
                modifier = modifier
                    .background(color = EmpathTheme.colors.surface)
                    .verticalScroll(scrollState)
                    .imePadding()
                    .screenPadding(PaddingType.AUTH),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Logo()
                Spacer(
                    modifier = Modifier
                        .heightIn(min = 30.dp, max = 60.dp)
                        .fillMaxHeight(),
                )
                PrimaryAuthorizationContent(
                    modifier = Modifier.defaultMaxWidth(),
                    email = loginState.email,
                    isEmailValid = loginState.isEmailValid,
                    onEmailChange = { email -> onEvent(LoginEvent.EmailChange(email)) },
                    password = loginState.password,
                    isPasswordValid = loginState.isPasswordValid,
                    onPasswordChange = { password -> onEvent(LoginEvent.PasswordChange(password)) },
                    onLoginClick = { onEvent(LoginEvent.LogIn) },
                    onSignUpClick = { onEvent(LoginEvent.SignUp) },
                    onResetPasswordClick = { onEvent(LoginEvent.ResetPassword) },
                )
                TitledDivider()
                SecondaryAuthButtons(
                    modifier = Modifier.defaultMaxWidth(),
                    onGoogleAuthClick = { onEvent(LoginEvent.GoogleAuthClick) },
                    onFacebookAuthClick = { onEvent(LoginEvent.FacebookAuthClick) },
                )
            }
        }

        is LoginState.Loading -> {
            CircularLoadingScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }

        is LoginState.Error -> Unit
        is LoginState.Initial -> Unit
    }
}

@Composable
private fun PrimaryAuthorizationContent(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    password: String,
    isPasswordValid: Boolean,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            isError = isEmailValid.not(),
            label = {
                Text(
                    text = stringResource(Res.string.email),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            isError = isPasswordValid.not(),
            label = {
                Text(
                    text = stringResource(Res.string.password),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
        )

        Row(
            modifier = Modifier.defaultMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onResetPasswordClick,
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.surfaceContainer,
                    contentColor = EmpathTheme.colors.onSurface,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.reset_password),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = onLoginClick,
                enabled = email.isNotBlank() && password.isNotBlank() && isEmailValid && isPasswordValid,
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.login),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        SignUpText(onSignUpClick)
    }
}

@Composable
private fun SignUpText(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.sign_up_prompt))
            appendSpace()
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = "SIGN UP",
                    styles = TextLinkStyles(
                        style = EmpathTheme.typography.bodyMedium.copy(
                            color = EmpathTheme.colors.primary,
                        ).toSpanStyle()
                    )
                ) {
                    onSignUpClick()
                },
            ) {
                append(stringResource(Res.string.sign_up))
            }
        },
        style = EmpathTheme.typography.bodyMedium,
    )
}

@Preview
@Composable
private fun Preview(){
    LoginScreen(
        component = FakeLoginComponent(),
    )
}
