package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.components.SelectedSkills
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.components.Skills
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SkillsDialog(
    component: SkillsDialogComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState()
    val skills = component.skills.collectAsLazyPagingItems()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    if (windowAdaptiveInfo.isPhone()) {
        SkillsBottomSheetDialog(
            modifier = Modifier,
            skills = skills,
            state = state.value,
            onEvent = component::onEvent,
        )
    } else {
        SkillsDialog(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            skills = skills,
            state = state.value,
            onEvent = component::onEvent,
        )
    }
}

@Composable
private fun SkillsDialog(
    modifier: Modifier,
    state: SkillsState,
    skills: LazyPagingItems<SkillUi>,
    onEvent: (SkillsEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(SkillsEvent.DismissClick) },
    ) {
        Card(
            shape = EmpathTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurface,
            ),
        ) {
            SkillsDialogContent(
                modifier = modifier,
                state = state,
                skills = skills,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkillsBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: SkillsState,
    skills: LazyPagingItems<SkillUi>,
    onEvent: (SkillsEvent) -> Unit,
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
                onEvent(SkillsEvent.DismissClick)
            }
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) {
        SkillsDialogContent(
            modifier = modifier
                .fillMaxWidth()
                .screenPadding(PaddingType.DIALOG),
            state = state,
            skills = skills,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun SkillsDialogContent(
    modifier: Modifier = Modifier,
    state: SkillsState,
    skills: LazyPagingItems<SkillUi>,
    onEvent: (SkillsEvent) -> Unit,
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
                text = stringResource(Res.string.adding_skills_title),
                style = EmpathTheme.typography.titleLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = stringResource(Res.string.adding_skills_description),
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
        SelectedSkills(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onEvent = onEvent,
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.query,
            shape = EmpathTheme.shapes.small,
            onValueChange = { query ->
                onEvent(SkillsEvent.Search(query))
            },
            enabled = state.isQueryValidLength(),
            label = {
                Text(
                    text = stringResource(Res.string.skill_name),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )
        Skills(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            skills = skills,
            onEvent = onEvent,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            TextButton(
                onClick = { onEvent(SkillsEvent.DismissClick) },
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
                onClick = { onEvent(SkillsEvent.SkillsSelectClick) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.select_skills),
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }
    }
}

