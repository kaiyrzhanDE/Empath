package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SkillCreateCard(
    state: SkillsState,
    onEvent: (SkillsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CollapseAnimatedVisibility(visible = state.query.isNotBlank()) {
            Text(
                text = buildString {
                    append("\"${state.query}\"")
                    appendSpace()
                    append(stringResource(Res.string.skill_not_found))
                },
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onEvent(SkillsEvent.SkillCreate) },
            enabled = state.hasSkill().not() && state.query.isNotBlank(),
        ) {
            Text(
                text = stringResource(Res.string.create_skill),
                style = EmpathTheme.typography.labelLarge,
            )
        }
    }
}
