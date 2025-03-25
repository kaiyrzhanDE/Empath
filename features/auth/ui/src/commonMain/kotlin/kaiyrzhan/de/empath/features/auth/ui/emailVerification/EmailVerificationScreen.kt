package kaiyrzhan.de.empath.features.auth.ui.emailVerification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.features.auth.ui.components.TopBar
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationAction
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationEvent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.model.EmailVerificationState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.text.isNotBlank

@Composable
internal fun EmailVerificationScreen(
    component: EmailVerificationComponent,
    modifier: Modifier = Modifier,
) {
    val emailVerificationState = component.state.collectAsState()
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
            is EmailVerificationAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = action.message,
                    )
                }
            }
        }
    }

    EmailVerificationScreen(
        modifier = modifier,
        state = emailVerificationState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun EmailVerificationScreen(
    modifier: Modifier = Modifier,
    state: EmailVerificationState,
    onEvent: (EmailVerificationEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    when (state) {
        is EmailVerificationState.Success -> {
            Column(
                modifier = modifier
                    .background(color = EmpathTheme.colors.surface)
                    .verticalScroll(scrollState)
                    .imePadding()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBar(
                    title = stringResource(FeatureRes.string.enter_email_title),
                    description = stringResource(FeatureRes.string.enter_email_description),
                    onBackClick = { onEvent(EmailVerificationEvent.BackClick) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    modifier = Modifier.defaultMaxWidth(),
                    value = state.email,
                    isError = state.isEmailValid.not(),
                    onValueChange = { email -> onEvent(EmailVerificationEvent.EmailChange(email)) },
                    label = {
                        Text(
                            text = stringResource(FeatureRes.string.email),
                            style = EmpathTheme.typography.bodyLarge,
                        )
                    },
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier.defaultMaxWidth(),
                    onClick = { onEvent(EmailVerificationEvent.SendCodeClick) },
                    enabled = state.email.isNotBlank(),
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(FeatureRes.string.send_code),
                        style = EmpathTheme.typography.labelLarge,
                        maxLines = 1,
                    )
                }
            }
        }

        is EmailVerificationState.Loading -> {
            CircularLoadingScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }
        is EmailVerificationState.Error -> Unit
        is EmailVerificationState.Initial -> Unit
    }
}

@Preview
@Composable
private fun Preview() {
    EmailVerificationScreen(
        component = FakeEmailVerificationComponent(),
    )
}
