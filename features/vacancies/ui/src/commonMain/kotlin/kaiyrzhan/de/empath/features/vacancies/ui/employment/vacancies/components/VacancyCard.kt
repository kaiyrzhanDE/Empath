package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.from
import empath.core.uikit.generated.resources.kzt
import empath.core.uikit.generated.resources.to
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun VacancyCard(
    modifier: Modifier = Modifier,
    vacancy: VacancyUi,
    onEvent: (VacanciesEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .noRippleClickable { onEvent(VacanciesEvent.VacancyDetailClick(vacancy)) },
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = vacancy.title,
                style = EmpathTheme.typography.headlineMedium,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = buildString {
                    append(stringResource(Res.string.from))
                    appendSpace()
                    append(vacancy.salary.from.toGroupedString())
                    appendSpace()
                    append(stringResource(Res.string.kzt))
                    appendSpace()
                    append(stringResource(Res.string.to))
                    appendSpace()
                    append(vacancy.salary.to.toGroupedString())
                    appendSpace()
                    append(stringResource(Res.string.kzt))
                },
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurface,
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            VacancyWorkingConditions(
                modifier = Modifier.fillMaxWidth(),
                vacancy = vacancy,
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            VacancyWorkingSkills(
                modifier = Modifier.fillMaxWidth(),
                vacancy = vacancy,
            )
            EmploymentActions(
                modifier = Modifier.fillMaxWidth(),
                vacancy = vacancy,
                onEvent = onEvent,
            )
        }

    }
}

