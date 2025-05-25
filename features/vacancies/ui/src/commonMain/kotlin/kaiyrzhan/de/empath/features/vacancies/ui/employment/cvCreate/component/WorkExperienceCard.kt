package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendRequiredMarker
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model.CvCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailEvent
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalUuidApi::class)
@Composable
internal fun WorkExperienceCard(
    modifier: Modifier = Modifier,
    workExperience: WorkExperienceUi,
    position: Int,
    onEvent: (CvCreateEvent) -> Unit
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
                onValueChange = { companyName ->
                    onEvent(
                        CvCreateEvent.WorkExperienceCompanyChange(
                            id = workExperience.id,
                            company = companyName,
                        )
                    )
                },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.company_name))
                            appendSpace()
                            append(position.plus(1).toString())
                            appendRequiredMarker()
                        },
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workExperience.title,
                shape = EmpathTheme.shapes.small,
                onValueChange = { title ->
                    onEvent(
                        CvCreateEvent.WorkExperienceTitleChange(
                            id = workExperience.id,
                            title = title,
                        )
                    )
                },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.title))
                            appendRequiredMarker()
                        },
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workExperience.description,
                shape = EmpathTheme.shapes.small,
                onValueChange = { description ->
                    onEvent(
                        CvCreateEvent.WorkExperienceDescriptionChange(
                            id = workExperience.id,
                            description = description,
                        )
                    )
                },
                textStyle = EmpathTheme.typography.bodyLarge,
                minLines = 4,
                label = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.company_description))
                            appendRequiredMarker()
                        },
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            Row(
                modifier = Modifier.noRippleClickable {
                    onEvent(CvCreateEvent.WorkExperienceIsRelevantChange(workExperience.id))
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RadioButton(
                    selected = workExperience.isRelevant,
                    onClick = {
                        onEvent(CvCreateEvent.WorkExperienceIsRelevantChange(workExperience.id))
                    },
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
                        onClick = {
                            onEvent(
                                CvCreateEvent.WorkExperienceStartDateChange(
                                    id = workExperience.id,
                                )
                            )
                        }
                    )

                    DatePickerField(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.end_date),
                        date = workExperience.endDate.dateFormat(),
                        onClick = {
                            onEvent(
                                CvCreateEvent.WorkExperienceEndDateChange(
                                    id = workExperience.id,
                                )
                            )
                        }
                    )
                }
            }
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { onEvent(CvCreateEvent.WorkExperienceRemove(workExperience.id)) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.surfaceContainer,
                    contentColor = EmpathTheme.colors.onSurface,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.delete),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}