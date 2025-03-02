package kaiyrzhan.de.empath.features.auth.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.components.CircularLoading
import kaiyrzhan.de.empath.core.modifiers.appendSpace
import kaiyrzhan.de.empath.core.modifiers.isPhone
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.components.Logo
import kaiyrzhan.de.empath.features.auth.ui.components.LowPolyBackground
import kaiyrzhan.de.empath.features.auth.ui.components.defaultMaxWidth
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginEvent
import kaiyrzhan.de.empath.features.auth.ui.login.model.LoginState
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import org.jetbrains.compose.resources.stringResource

@Composable
public fun LoginScreen(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()
    val loginState = component.state.collectAsState()

    if (currentWindowInfo.isPhone()) {
        LoginScreen(
            modifier = modifier.fillMaxSize(),
            loginState = loginState.value,
            onEvent = component::onEvent,
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LowPolyBackground(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp, horizontal = 12.dp),
            )
            LoginScreen(
                modifier = Modifier.weight(1f),
                loginState = loginState.value,
                onEvent = component::onEvent,
            )
        }
    }
}


@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {
    when (loginState) {
        is LoginState.Success -> {
            val scrollState = rememberScrollState()
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .imePadding()
                    .padding(24.dp),
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
                    email = loginState.email,
                    onEmailChange = { email -> onEvent(LoginEvent.EmailChange(email)) },
                    password = loginState.password,
                    onPasswordChange = { password -> onEvent(LoginEvent.PasswordChange(password)) },
                    onLoginClick = { onEvent(LoginEvent.LogIn) },
                    onSignUpClick = { onEvent(LoginEvent.SignUp) },
                    onResetPasswordClick = { onEvent(LoginEvent.ResetPassword) },
                )
                TitledDivider()
                SecondaryAuthContent(
                    onGoogleAuthClick = { onEvent(LoginEvent.GoogleAuthClick) },
                    onFacebookAuthClick = { onEvent(LoginEvent.FacebookAuthClick) },
                )

            }
        }

        is LoginState.Loading -> {
            CircularLoading(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            )
        }

        is LoginState.Error -> Unit
        is LoginState.Initial -> Unit
    }
}

@Composable
private fun TitledDivider(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .defaultMaxWidth()
            .padding(vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(FeatureRes.string.dividers_placeholder),
            style = EmpathTheme.typography.bodyMedium,
            color = EmpathTheme.colors.outlineVariant,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SecondaryAuthContent(
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
) {
    Column(
        modifier = Modifier.defaultMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onGoogleAuthClick,
            shape = EmpathTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.onScrim,
                contentColor = EmpathTheme.colors.shadow,
                disabledContentColor = EmpathTheme.colors.onSurface,
                disabledContainerColor = EmpathTheme.colors.surfaceBright
            ),
        ) {
            Text(
                text = stringResource(FeatureRes.string.continue_with_google),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onFacebookAuthClick,
            shape = EmpathTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.primary,
                contentColor = EmpathTheme.colors.onPrimary,
                disabledContentColor = EmpathTheme.colors.onSurface,
                disabledContainerColor = EmpathTheme.colors.surfaceBright
            ),
        ) {
            Text(
                text = stringResource(FeatureRes.string.continue_with_facebook),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun PrimaryAuthorizationContent(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
) {
    Column(
        modifier = modifier.defaultMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = {
                Text(
                    text = stringResource(FeatureRes.string.email),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = {
                Text(
                    text = stringResource(FeatureRes.string.password),
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
                    text = stringResource(FeatureRes.string.reset_password),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = onLoginClick,
                enabled = email.isNotBlank() && password.isNotBlank(),
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                    disabledContentColor = EmpathTheme.colors.onSurface,
                    disabledContainerColor = EmpathTheme.colors.surfaceBright
                ),
            ) {
                Text(
                    text = stringResource(FeatureRes.string.login),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                )
            }
        }

        Text(
            text = buildAnnotatedString {
                append(stringResource(FeatureRes.string.sign_up_prompt))
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
                    append(stringResource(FeatureRes.string.sign_up))
                }
            },
            style = EmpathTheme.typography.bodyMedium,
        )
    }
}
