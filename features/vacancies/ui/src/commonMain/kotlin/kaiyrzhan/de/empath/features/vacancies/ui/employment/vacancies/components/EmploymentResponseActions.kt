package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.components

import androidx.compose.animation.animateContentSize
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
import empath.core.uikit.generated.resources.cancel
import empath.core.uikit.generated.resources.respond
import empath.core.uikit.generated.resources.responded
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EmploymentActions(
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
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (vacancy.status.canBeCanceled()) {
                Button(
                    onClick = {
                        //TODO(cancel response)
                    },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        style = EmpathTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Button(
                onClick = {
                    onEvent(VacanciesEvent.ResponseToVacancy(vacancy))
                },
                shape = EmpathTheme.shapes.small,
                enabled = vacancy.status.canRespond(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(
                        resource = if (vacancy.status.canRespond()) Res.string.respond
                        else Res.string.responded,
                    ),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}