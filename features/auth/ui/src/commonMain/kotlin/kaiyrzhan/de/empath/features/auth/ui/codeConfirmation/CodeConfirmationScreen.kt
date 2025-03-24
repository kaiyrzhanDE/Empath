package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.dialog.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.features.auth.ui.components.TopBar
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationAction
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CodeConfirmationScreen(
    component: CodeConfirmationComponent,
    modifier: Modifier = Modifier,
) {
    val codeConfirmationState = component.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val messageDialogSlot by component.messageDialog.subscribeAsState()
    messageDialogSlot.child?.instance?.also { messageComponent ->
        MessageDialog(
            component = messageComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is CodeConfirmationAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    CodeConfirmationScreen(
        modifier = modifier,
        state = codeConfirmationState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun CodeConfirmationScreen(
    state: CodeConfirmationState,
    onEvent: (CodeConfirmationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    when (state) {
        is CodeConfirmationState.Success -> {
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
                    title = stringResource(FeatureRes.string.enter_code_title),
                    description = buildAnnotatedString {
                        append(stringResource(FeatureRes.string.enter_code_description))
                        appendLine()
                        withStyle(
                            style = EmpathTheme.typography.labelLarge.toSpanStyle().copy(
                                color = EmpathTheme.colors.onSurface,
                            ),
                        ) {
                            append(state.email)
                        }
                    },
                    onBackClick = { onEvent(CodeConfirmationEvent.BackClick) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    modifier = Modifier.defaultMaxWidth(),
                    isError = state.isCodeValid.not(),
                    value = state.code,
                    onValueChange = { code -> onEvent(CodeConfirmationEvent.CodeChange(code)) },
                    label = {
                        Text(
                            text = stringResource(FeatureRes.string.code),
                            style = EmpathTheme.typography.bodyLarge,
                        )
                    },
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.defaultMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onEvent(CodeConfirmationEvent.ResendClick) },
                        shape = EmpathTheme.shapes.small,
                        enabled = state.isResendAllowed,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.surfaceContainer,
                            contentColor = EmpathTheme.colors.onSurface,
                        ),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(FeatureRes.string.resend))
                                appendSpace()
                                if (state.isResendAllowed.not()) {
                                    append("(${state.resentTimer})")
                                }
                            },
                            style = EmpathTheme.typography.labelLarge,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onEvent(CodeConfirmationEvent.CodeVerify) },
                        enabled = state.canCheckCode(),
                        shape = EmpathTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.primary,
                            contentColor = EmpathTheme.colors.onPrimary,
                        ),
                    ) {
                        Text(
                            text = stringResource(FeatureRes.string.check),
                            style = EmpathTheme.typography.labelLarge,
                        )
                    }
                }

            }
        }
        is CodeConfirmationState.Loading -> {
            CircularLoadingScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }
        is CodeConfirmationState.Error -> Unit
        is CodeConfirmationState.Initial -> Unit
    }
}

@Preview
@Composable
private fun Preview(){
    CodeConfirmationScreen(
        component = FakeCodeConfirmationComponent(),
    )
}
