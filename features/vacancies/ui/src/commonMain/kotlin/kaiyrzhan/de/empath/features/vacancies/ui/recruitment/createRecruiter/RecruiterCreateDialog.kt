package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model.RecruiterCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model.RecruiterCreateState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecruiterCreateDialog(
    component: RecruiterCreateDialogComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        RecruiterCreateBottomSheetDialog(
            modifier = modifier,
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        RecruiterCreateDialog(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state.value,
            onEvent = component::onEvent,
        )
    }

}

@Composable
private fun RecruiterCreateDialog(
    modifier: Modifier,
    state: RecruiterCreateState,
    onEvent: (RecruiterCreateEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(RecruiterCreateEvent.DismissClick) },
    ) {
        Card(
            shape = EmpathTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            RecruiterCreateDialogContent(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecruiterCreateBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: RecruiterCreateState,
    onEvent: (RecruiterCreateEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                onEvent(RecruiterCreateEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        RecruiterCreateDialogContent(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun RecruiterCreateDialogContent(
    modifier: Modifier = Modifier,
    state: RecruiterCreateState,
    onEvent: (RecruiterCreateEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(Res.string.vacancy_creation_title),
                style = EmpathTheme.typography.titleLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = stringResource(Res.string.vacancy_creation_description),
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.companyName,
            shape = EmpathTheme.shapes.small,
            onValueChange = { companyName ->
                onEvent(RecruiterCreateEvent.CompanyNameChange(companyName))
            },
            label = {
                Text(
                    text = stringResource(Res.string.company_name),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.companyDescription,
            shape = EmpathTheme.shapes.small,
            onValueChange = { companyDescription ->
                onEvent(RecruiterCreateEvent.CompanyDescriptionChange(companyDescription))
            },
            minLines = 3,
            label = {
                Text(
                    text = stringResource(Res.string.company_description),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            shape = EmpathTheme.shapes.small,
            onValueChange = { email ->
                onEvent(RecruiterCreateEvent.EmailChange(email))
            },
            maxLines = 1,
            label = {
                Text(
                    text = stringResource(Res.string.email),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            ),
            leadingIcon = {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_alternate_email),
                        contentDescription = null,
                    )
                }
            }
        )
        if (state.errorMessage.isNotBlank()) {
            Text(
                text = state.errorMessage,
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.error,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            TextButton(
                onClick = { onEvent(RecruiterCreateEvent.DismissClick) },
                shape = EmpathTheme.shapes.small,
                enabled = state.isLoading.not(),
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(
                    text = stringResource(Res.string.close),
                    style = EmpathTheme.typography.labelLarge,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onEvent(RecruiterCreateEvent.ClickCreate) },
                shape = EmpathTheme.shapes.small,
                enabled = state.isLoading.not(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.save),
                        style = EmpathTheme.typography.labelLarge,
                    )
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            trackColor = EmpathTheme.colors.onSurfaceVariant,
                            strokeCap = StrokeCap.Square,
                            color = EmpathTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }

}
