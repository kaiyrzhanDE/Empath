package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_work_outlined
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyUi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun VacancyWorkingSkills(
    modifier: Modifier = Modifier,
    vacancy: VacancyUi,
) {
    if (vacancy.hasSkills()) {
        FlowRow(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            vacancy.skills.forEach { skill ->
                WorkingSkillCard(
                    skill = skill,
                    containerColor = EmpathTheme.colors.primaryContainer,
                )
            }
            vacancy.additionalSkills.forEach { skill ->
                WorkingSkillCard(
                    skill = skill,
                    containerColor = EmpathTheme.colors.secondaryContainer,
                )
            }
        }
    }
}
