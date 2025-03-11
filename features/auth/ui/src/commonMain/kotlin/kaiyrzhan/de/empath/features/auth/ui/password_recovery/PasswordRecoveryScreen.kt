package kaiyrzhan.de.empath.features.auth.ui.password_recovery

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.components.CircularLoading
import kaiyrzhan.de.empath.core.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.auth.ui.components.PasswordOutlinedTextField
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.features.auth.ui.components.TopBar
import kaiyrzhan.de.empath.features.auth.ui.components.defaultMaxWidth
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
public fun PasswordRecoveryScreen(
    component: PasswordRecoveryComponent,
    modifier: Modifier = Modifier,
) {
    val passwordRecoveryState = component.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    SingleEventEffect(component.action) { action ->
        when (action) {
            is PasswordRecoveryAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    PasswordRecoveryScreen(
        modifier = modifier.fillMaxSize(),
        state = passwordRecoveryState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun PasswordRecoveryScreen(
    modifier: Modifier = Modifier,
    state: PasswordRecoveryState,
    onEvent: (PasswordRecoveryEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    when (state) {
        is PasswordRecoveryState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .imePadding()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBar(
                    title = stringResource(FeatureRes.string.enter_new_password_title),
                    description = stringResource(FeatureRes.string.enter_new_password_description),
                    onBackClick = { onEvent(PasswordRecoveryEvent.BackClick) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                PasswordOutlinedTextField(
                    value = state.password,
                    onValueChange = { password ->
                        onEvent(PasswordRecoveryEvent.PasswordChange(password))
                    },
                    arePasswordsMatching = state.arePasswordsMatching,
                    label = stringResource(FeatureRes.string.password),
                    isValueVisible = state.isPasswordVisible,
                    onShowClick = { onEvent(PasswordRecoveryEvent.PasswordShow) },
                )
                Spacer(modifier = Modifier.height(20.dp))
                PasswordOutlinedTextField(
                    value = state.repeatedPassword,
                    onValueChange = { password ->
                        onEvent(PasswordRecoveryEvent.RepeatedPasswordChange(password))
                    },
                    arePasswordsMatching = state.arePasswordsMatching,
                    label = stringResource(FeatureRes.string.repeated_password),
                    isValueVisible = state.isRepeatedPasswordVisible,
                    onShowClick = { onEvent(PasswordRecoveryEvent.RepeatedPasswordShow) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.defaultMaxWidth(),
                    onClick = { onEvent(PasswordRecoveryEvent.PasswordReset) },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(FeatureRes.string.reset_password),
                        style = EmpathTheme.typography.labelLarge,
                        maxLines = 1,
                    )
                }
            }
        }

        is PasswordRecoveryState.Loading -> {
            CircularLoading()
        }

        is PasswordRecoveryState.Error -> Unit
        is PasswordRecoveryState.Initial -> Unit
    }

}
