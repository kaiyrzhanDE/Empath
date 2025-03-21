package kaiyrzhan.de.empath.features.auth.ui.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoading
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.auth.ui.components.PasswordOutlinedTextField
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.features.auth.ui.components.TopBar
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.navigation.BackHandler
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SignUpScreen(
    component: SignUpComponent,
    modifier: Modifier = Modifier,
) {
    val signUpState = component.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val messageDialogSlot by component.messageDialog.subscribeAsState()
    messageDialogSlot.child?.instance?.also { messageComponent ->
        MessageDialog(
            component = messageComponent,
        )
    }

    BackHandler(component.backHandler) {
        component.onEvent(SignUpEvent.BackClick)
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is SignUpAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    SignUpScreen(
        state = signUpState.value,
        onEvent = component::onEvent,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    when (state) {
        is SignUpState.Success -> {
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .imePadding()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBar(
                    title = stringResource(FeatureRes.string.enter_fields_title),
                    description = stringResource(FeatureRes.string.enter_fields_description),
                    onBackClick = { onEvent(SignUpEvent.BackClick) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                UserDataTextFields(state, onEvent)
                Spacer(modifier = Modifier.height(20.dp))
                UserAgreementCheckBox(state, onEvent)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier.defaultMaxWidth(),
                    onClick = { onEvent(SignUpEvent.SignUp) },
                    shape = EmpathTheme.shapes.small,
                    enabled = state.canSignUp(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(FeatureRes.string.sign_up),
                        style = EmpathTheme.typography.labelLarge,
                        maxLines = 1,
                    )
                }
            }
        }

        is SignUpState.Loading -> CircularLoading()
        is SignUpState.Error -> Unit
        is SignUpState.Initial -> Unit
    }

}

@Composable
private fun ColumnScope.UserDataTextFields(
    state: SignUpState.Success,
    onEvent: (SignUpEvent) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.defaultMaxWidth(),
        value = state.nickname,
        onValueChange = { nickname -> onEvent(SignUpEvent.NicknameChange(nickname)) },
        label = {
            Text(
                text = stringResource(FeatureRes.string.nickname),
                style = EmpathTheme.typography.bodyLarge,
            )
        },
        maxLines = 1,
    )
    Spacer(modifier = Modifier.height(20.dp))
    PasswordOutlinedTextField(
        value = state.password,
        onValueChange = { password ->
            onEvent(SignUpEvent.PasswordChange(password))
        },
        label = stringResource(FeatureRes.string.password),
        arePasswordsMatching = state.arePasswordsMatching,
        isPasswordValid = state.isPasswordValid,
        isValueVisible = state.isPasswordVisible,
        onShowClick = { onEvent(SignUpEvent.PasswordShow) },
    )
    Spacer(modifier = Modifier.height(20.dp))
    PasswordOutlinedTextField(
        value = state.repeatedPassword,
        onValueChange = { password ->
            onEvent(SignUpEvent.RepeatedPasswordChange(password))
        },
        label = stringResource(FeatureRes.string.repeated_password),
        arePasswordsMatching = state.arePasswordsMatching,
        isPasswordValid = state.isRepeatedPasswordValid,
        isValueVisible = state.isRepeatedPasswordVisible,
        onShowClick = { onEvent(SignUpEvent.RepeatedPasswordShow) },
    )
}

@Composable
private fun ColumnScope.UserAgreementCheckBox(
    state: SignUpState.Success,
    onEvent: (SignUpEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.defaultMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Checkbox(
            checked = state.isUserAgreementAccepted,
            onCheckedChange = { checked ->
                onEvent(SignUpEvent.UserAgreementAccept(checked))
            },
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(FeatureRes.string.user_agreement_prompt))
                appendSpace()
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "USER AGREEMENT",
                        styles = TextLinkStyles(
                            style = EmpathTheme.typography.labelSmall.copy(
                                color = EmpathTheme.colors.onSurface,
                            ).toSpanStyle()
                        )
                    ) {
                        onEvent(SignUpEvent.UserAgreementClick)
                    },
                ) {
                    append(stringResource(FeatureRes.string.user_agreement_and_privacy_policy))
                }
            },
            style = EmpathTheme.typography.labelSmall,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SignUpScreen(
        component = FakeSignUpComponent()
    )
}
