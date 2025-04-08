package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_calendar_today
import empath.core.uikit.generated.resources.ic_distance
import empath.core.uikit.generated.resources.ic_domain
import empath.core.uikit.generated.resources.ic_schedule
import empath.core.uikit.generated.resources.ic_work_history
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingConditionCard
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun VacancyWorkingConditions(
    modifier: Modifier = Modifier,
    vacancy: VacancyUi,
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        WorkingConditionCard(
            skills = vacancy.workFormats,
            painter = painterResource(Res.drawable.ic_domain),
        )
        WorkingConditionCard(
            skill = vacancy.workExperience,
            painter = painterResource(Res.drawable.ic_work_history),
        )
        WorkingConditionCard(
            skill = vacancy.address,
            painter = painterResource(Res.drawable.ic_distance),
        )
        WorkingConditionCard(
            skills = vacancy.workSchedules,
            painter = painterResource(Res.drawable.ic_calendar_today),
        )
        WorkingConditionCard(
            skills = vacancy.employmentTypes,
            painter = painterResource(Res.drawable.ic_schedule),
        )
    }
}