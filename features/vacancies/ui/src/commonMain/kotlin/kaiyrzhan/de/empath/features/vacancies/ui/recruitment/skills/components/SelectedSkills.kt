package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.selected_tags
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectedSkills(
    modifier: Modifier = Modifier,
    state: SkillsState,
    onEvent: (SkillsEvent) -> Unit,
) {
    val selectedTagsListState = rememberLazyListState()
    if (state.editableSkills.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(Res.string.selected_tags),
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = selectedTagsListState,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(state.editableSkills) { skill ->
                    Box(
                        modifier = Modifier
                            .clip(EmpathTheme.shapes.small)
                            .background(EmpathTheme.colors.secondaryContainer)
                            .clickable { onEvent(SkillsEvent.SkillRemove(skill)) }
                            .padding(vertical = 6.dp, horizontal = 16.dp),
                    ) {
                        Text(
                            text = skill.name,
                            style = EmpathTheme.typography.labelMedium,
                            color = EmpathTheme.colors.onSecondaryContainer,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}