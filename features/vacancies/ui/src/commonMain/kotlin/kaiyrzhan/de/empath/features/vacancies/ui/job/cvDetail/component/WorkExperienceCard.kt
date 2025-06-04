package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.company_description
import empath.core.uikit.generated.resources.company_name
import empath.core.uikit.generated.resources.date_of_employment
import empath.core.uikit.generated.resources.end_date
import empath.core.uikit.generated.resources.is_relevant
import empath.core.uikit.generated.resources.start_date
import empath.core.uikit.generated.resources.title
import empath.core.uikit.generated.resources.work_experience_description
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendRequiredMarker
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.features.vacancies.ui.components.DatePickerField
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.WorkExperienceUi
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
internal fun WorkExperienceCard(
    modifier: Modifier = Modifier,
    workExperience: WorkExperienceUi,
    position: Int,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
        border = BorderStroke(
            color = if (workExperience.isRelevant) EmpathTheme.colors.tertiary
            else EmpathTheme.colors.outlineVariant,
            width = 1.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workExperience.companyName,
                shape = EmpathTheme.shapes.small,
                onValueChange = { },
                enabled = false,
                textStyle = EmpathTheme.typography.bodyLarge,
                label = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.company_name))
                            appendSpace()
                            append(position.plus(1).toString())
                        },
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = EmpathTheme.colors.onSurface,
                ),
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workExperience.title,
                shape = EmpathTheme.shapes.small,
                enabled = false,
                onValueChange = { },
                textStyle = EmpathTheme.typography.bodyLarge,
                label = {
                    Text(
                        text = stringResource(Res.string.title),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = EmpathTheme.colors.onSurface,
                ),
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workExperience.description,
                shape = EmpathTheme.shapes.small,
                onValueChange = { },
                enabled = false,
                textStyle = EmpathTheme.typography.bodyLarge,
                label = {
                    Text(
                        text = stringResource(Res.string.work_experience_description),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = EmpathTheme.colors.onSurface,
                ),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RadioButton(
                    selected = workExperience.isRelevant,
                    onClick = {},
                )
                Text(
                    text = stringResource(Res.string.is_relevant),
                    style = EmpathTheme.typography.bodyLarge,
                    color = EmpathTheme.colors.onSurface,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(Res.string.date_of_employment))
                        appendColon()
                    },
                    style = EmpathTheme.typography.labelLarge,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DatePickerField(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.start_date),
                        date = workExperience.startDate.dateFormat(),
                    )

                    DatePickerField(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.end_date),
                        date = workExperience.endDate.dateFormat(),
                    )
                }
            }
        }
    }
}