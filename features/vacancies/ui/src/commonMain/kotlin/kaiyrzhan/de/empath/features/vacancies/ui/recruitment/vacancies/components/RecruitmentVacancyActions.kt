package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.edit
import empath.core.uikit.generated.resources.hide
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecruitmentVacancyActions(
    modifier: Modifier = Modifier,
    vacancy: VacancyUi,
    onEvent: (VacanciesEvent) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = vacancy.dateOfCreated.dateFormat(),
            style = EmpathTheme.typography.bodyMedium,
            color = EmpathTheme.colors.onSurfaceVariant,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Button(
                onClick = { onEvent(VacanciesEvent.VacancyHideClick(vacancy.id)) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.surfaceContainer,
                    contentColor = EmpathTheme.colors.onSurface,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.hide),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Button(
                onClick = { onEvent(VacanciesEvent.VacancyEditClick(vacancy.id)) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.edit),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
