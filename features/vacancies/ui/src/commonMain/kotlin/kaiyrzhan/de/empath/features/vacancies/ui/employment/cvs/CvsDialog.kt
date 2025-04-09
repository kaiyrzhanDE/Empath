package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingCard
import kaiyrzhan.de.empath.core.ui.components.ErrorCard
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.components.Cvs
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CvsDialog(
    component: CvsDialogComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        CvsBottomSheetDialog(
            modifier = modifier,
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        CvsDialog(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state.value,
            onEvent = component::onEvent,
        )
    }
}

@Composable
private fun CvsDialog(
    modifier: Modifier,
    state: CvsState,
    onEvent: (CvsEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(CvsEvent.DismissClick) },
    ) {
        Card(
            shape = EmpathTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            CvsDialogContent(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CvsBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: CvsState,
    onEvent: (CvsEvent) -> Unit,
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
                onEvent(CvsEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        CvsDialogContent(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun CvsDialogContent(
    modifier: Modifier = Modifier,
    state: CvsState,
    onEvent: (CvsEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (state) {
            is CvsState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.selecting_cv_title),
                        style = EmpathTheme.typography.titleLarge,
                        color = EmpathTheme.colors.onSurface,
                    )
                    Text(
                        text = stringResource(Res.string.selecting_cv_description),
                        style = EmpathTheme.typography.bodyLarge,
                        color = EmpathTheme.colors.onSurfaceVariant,
                    )
                }
                Cvs(
                    modifier = Modifier.fillMaxWidth(),
                    cvs = state.cvs,
                    onEvent = onEvent,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    TextButton(
                        onClick = { onEvent(CvsEvent.DismissClick) },
                        shape = EmpathTheme.shapes.small,
                        colors = ButtonDefaults.textButtonColors(),
                    ) {
                        Text(
                            text = stringResource(Res.string.close),
                            style = EmpathTheme.typography.labelLarge,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onEvent(CvsEvent.CvSelectClick) },
                        shape = EmpathTheme.shapes.small,
                        enabled = state.cvs.any{ it.isSelected },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.primary,
                            contentColor = EmpathTheme.colors.onPrimary,
                        ),
                    ) {
                        Text(
                            text = stringResource(Res.string.select_cv),
                            style = EmpathTheme.typography.labelLarge,
                        )
                    }
                }
            }

            is CvsState.Loading -> {
                CircularLoadingCard(
                    modifier = Modifier
                        .heightIn(min = 400.dp, max = 500.dp)
                        .fillMaxSize(),
                )
            }

            is CvsState.Error -> {
                ErrorCard(
                    modifier = Modifier
                        .heightIn(min = 400.dp, max = 500.dp)
                        .fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = { onEvent(CvsEvent.ReloadCvs) },
                )
            }

            is CvsState.Initial -> Unit
        }
    }
}

