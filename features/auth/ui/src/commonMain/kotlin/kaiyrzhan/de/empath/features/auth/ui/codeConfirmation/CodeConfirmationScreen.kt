package kaiyrzhan.de.empath.features.auth.ui.codeConfirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.components.CircularLoading
import kaiyrzhan.de.empath.core.modifiers.appendSpace
import kaiyrzhan.de.empath.core.modifiers.isPhone
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationEvent
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.model.CodeConfirmationState
import kaiyrzhan.de.empath.features.auth.ui.components.LowPolyBackground
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.features.auth.ui.components.TopBar
import kaiyrzhan.de.empath.features.auth.ui.components.defaultMaxWidth
import org.jetbrains.compose.resources.stringResource
import kotlin.text.isNotBlank

@Composable
public fun CodeConfirmationScreen(
    component: CodeConfirmationComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()
    val codeConfirmationState = component.state.collectAsState()

    if (currentWindowInfo.isPhone()) {
        CodeConfirmationScreen(
            modifier = modifier.fillMaxSize(),
            state = codeConfirmationState.value,
            onEvent = component::onEvent,
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LowPolyBackground(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp, horizontal = 12.dp),
            )
            CodeConfirmationScreen(
                modifier = modifier.weight(1f),
                state = codeConfirmationState.value,
                onEvent = component::onEvent,
            )
        }
    }

}

@Composable
private fun CodeConfirmationScreen(
    modifier: Modifier = Modifier,
    state: CodeConfirmationState,
    onEvent: (CodeConfirmationEvent) -> Unit,
) {
    when (state) {
        is CodeConfirmationState.Success -> {
            val scrollState = rememberScrollState()
            Column(
                modifier = modifier
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
                            maxLines = 1,
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onEvent(CodeConfirmationEvent.CodeCheck) },
                        enabled = state.code.isNotBlank(),
                        shape = EmpathTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.primary,
                            contentColor = EmpathTheme.colors.onPrimary,
                            disabledContentColor = EmpathTheme.colors.onSurface,
                            disabledContainerColor = EmpathTheme.colors.surfaceBright
                        ),
                    ) {
                        Text(
                            text = stringResource(FeatureRes.string.check),
                            style = EmpathTheme.typography.labelLarge,
                            maxLines = 1,
                        )
                    }
                }

            }
        }

        is CodeConfirmationState.Loading -> {
            CircularLoading(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            )
        }

        is CodeConfirmationState.Error -> Unit
        is CodeConfirmationState.Initial -> Unit
    }

}
