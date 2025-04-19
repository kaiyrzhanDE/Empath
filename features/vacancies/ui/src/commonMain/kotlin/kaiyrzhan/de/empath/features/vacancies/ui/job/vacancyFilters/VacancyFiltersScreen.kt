package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.ThousandSeparatorTransformation
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toIntLimited
import kaiyrzhan.de.empath.features.vacancies.ui.components.FiltersCard
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.components.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersState
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkFormatUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacancyFiltersScreen(
    modifier: Modifier = Modifier,
    component: VacancyFiltersComponent,
) {
    val state = component.state.collectAsState()

    VacancyFiltersScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}


@Composable
private fun VacancyFiltersScreen(
    modifier: Modifier = Modifier,
    state: VacancyFiltersState,
    onEvent: (VacancyFiltersEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(contentPadding)
                .screenHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.query,
                shape = EmpathTheme.shapes.small,
                onValueChange = { query -> onEvent(VacancyFiltersEvent.QueryChange(query)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.title),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.includeWords,
                shape = EmpathTheme.shapes.small,
                onValueChange = { word -> onEvent(VacancyFiltersEvent.IncludeWordsChange(word)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.include_words),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.excludeWords,
                shape = EmpathTheme.shapes.small,
                onValueChange = { query -> onEvent(VacancyFiltersEvent.ExcludeWordsChange(query)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.exclude_words),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(Res.string.salary))
                        appendColon()
                    },
                    style = EmpathTheme.typography.labelLarge,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.salaryFrom?.toString().orEmpty(),
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { from ->
                            onEvent(
                                VacancyFiltersEvent.SalaryFromChange(
                                    salaryFrom = from.toIntLimited(),
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        visualTransformation = ThousandSeparatorTransformation(),
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 1,
                        label = {
                            Text(
                                text = stringResource(Res.string.from),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.salaryTo?.toString().orEmpty(),
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { to ->
                            onEvent(
                                VacancyFiltersEvent.SalaryToChange(
                                    salaryTo = to.toIntLimited(),
                                )
                            )
                        },
                        visualTransformation = ThousandSeparatorTransformation(),
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 1,
                        label = {
                            Text(
                                text = stringResource(Res.string.to),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )
                }
            }

            FiltersCard<WorkExperienceUi>(
                modifier = Modifier.fillMaxWidth(),
                filters = state.workExperiences,
                title = stringResource(Res.string.select_work_experiences),
                leadingPainter = painterResource(Res.drawable.ic_work_history),
                onSelect = { workExperience ->
                    onEvent(VacancyFiltersEvent.WorkExperienceSelect(workExperience))
                },
                anySelected = { workExperiences -> workExperiences.any { it.isSelected } },
                label = { workExperience -> stringResource(workExperience.type.res) },
                isSelected = { workExperience -> workExperience.isSelected }
            )

            FiltersCard<EducationUi>(
                modifier = Modifier.fillMaxWidth(),
                filters = state.educations,
                title = stringResource(Res.string.select_education),
                leadingPainter = painterResource(Res.drawable.ic_school),
                anySelected = { education -> education.any { it.isSelected } },
                onSelect = { education ->
                    onEvent(VacancyFiltersEvent.EducationSelect(education))
                },
                label = { education -> stringResource(education.type.res) },
                isSelected = { education -> education.isSelected }
            )

            FiltersCard<WorkFormatUi>(
                modifier = Modifier.fillMaxWidth(),
                filters = state.workFormats,
                title = stringResource(Res.string.select_work_formats),
                leadingPainter = painterResource(Res.drawable.ic_domain),
                anySelected = { workFormat -> workFormat.any { it.isSelected } },
                onSelect = { workFormat ->
                    onEvent(VacancyFiltersEvent.WorkFormatSelect(workFormat))
                },
                label = { workFormat -> stringResource(workFormat.type.res) },
                isSelected = { workFormat -> workFormat.isSelected }
            )

            Row(
                modifier = Modifier
                    .defaultMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(VacancyFiltersEvent.Clear) },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.surfaceContainer,
                        contentColor = EmpathTheme.colors.onSurface,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.clear),
                        style = EmpathTheme.typography.labelLarge,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(VacancyFiltersEvent.Apply) },
                    shape = EmpathTheme.shapes.small,
                    enabled = state.isChanged(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.apply),
                        style = EmpathTheme.typography.labelLarge,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

