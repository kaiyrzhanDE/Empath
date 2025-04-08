package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.ThousandSeparatorTransformation
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.core.utils.toIntLimited
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.components.Filters
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.components.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacancyCreateScreen(
    modifier: Modifier = Modifier,
    component: VacancyCreateComponent,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val state = component.state.collectAsState()

    val messageDialogSlot by component.messageDialog.subscribeAsState()
    messageDialogSlot.child?.instance?.also { messageComponent ->
        MessageDialog(
            component = messageComponent,
        )
    }

    val skillsDialogSlot by component.skillsDialog.subscribeAsState()
    skillsDialogSlot.child?.instance?.also { skillsComponent ->
        SkillsDialog(
            component = skillsComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is VacancyCreateAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    VacancyCreateScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VacancyCreateScreen(
    modifier: Modifier = Modifier,
    state: VacancyCreateState,
    onEvent: (VacancyCreateEvent) -> Unit,
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

        when (state) {
            is VacancyCreateState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .screenHorizontalPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.title,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { title -> onEvent(VacancyCreateEvent.TitleChange(title)) },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 2,
                        label = {
                            Text(
                                text = stringResource(Res.string.title),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = buildString {
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
                                value = state.newVacancy.salaryFrom?.toString().orEmpty(),
                                shape = EmpathTheme.shapes.small,
                                onValueChange = { from ->
                                    onEvent(
                                        VacancyCreateEvent.SalaryFromChange(
                                            from.toIntLimited()
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
                                value = state.newVacancy.salaryTo?.toString().orEmpty(),
                                shape = EmpathTheme.shapes.small,
                                onValueChange = { to ->
                                    onEvent(
                                        VacancyCreateEvent.SalaryToChange(
                                            to.toIntLimited()
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

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.address,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { address ->
                            onEvent(
                                VacancyCreateEvent.AddressChange(
                                    address
                                )
                            )
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 2,
                        label = {
                            Text(
                                text = stringResource(Res.string.address),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                        leadingIcon = {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Res.drawable.ic_distance),
                                    contentDescription = null,
                                )
                            }
                        }
                    )

                    Filters<WorkExperienceUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.newVacancy.workExperiences,
                        title = stringResource(Res.string.select_work_experiences),
                        leadingPainter = painterResource(Res.drawable.ic_work_history),
                        onSelect = { workExperience ->
                            onEvent(VacancyCreateEvent.WorkExperienceSelect(workExperience))
                        },
                        label = { workExperience -> stringResource(workExperience.type.res) },
                        isSelected = { workExperience -> workExperience.isSelected }
                    )

                    Filters<SkillUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.newVacancy.employmentTypes,
                        title = stringResource(Res.string.select_employments_types),
                        leadingPainter = painterResource(Res.drawable.ic_schedule),
                        onSelect = { employmentType ->
                            onEvent(VacancyCreateEvent.EmploymentTypeSelect(employmentType))
                        },
                        label = { employmentType -> employmentType.name },
                        isSelected = { employmentType -> employmentType.isSelected }
                    )

                    Filters<SkillUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.newVacancy.workFormats,
                        title = stringResource(Res.string.select_work_formats),
                        leadingPainter = painterResource(Res.drawable.ic_domain),
                        onSelect = { workFormat ->
                            onEvent(VacancyCreateEvent.WorkFormatSelect(workFormat))
                        },
                        label = { workFormat -> workFormat.name },
                        isSelected = { workFormat -> workFormat.isSelected }
                    )

                    Filters<SkillUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.newVacancy.workSchedules,
                        title = stringResource(Res.string.select_work_schedules),
                        leadingPainter = painterResource(Res.drawable.ic_calendar_today),
                        onSelect = { workSchedule ->
                            onEvent(VacancyCreateEvent.WorkScheduleSelect(workSchedule))
                        },
                        label = { workSchedule -> workSchedule.name },
                        isSelected = { workSchedule -> workSchedule.isSelected }
                    )

                    Filters<EducationUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.newVacancy.educations,
                        title = stringResource(Res.string.select_education),
                        leadingPainter = painterResource(Res.drawable.ic_school),
                        onSelect = { education ->
                            onEvent(VacancyCreateEvent.EducationSelect(education))
                        },
                        label = { education -> stringResource(education.type.res) },
                        isSelected = { education -> education.isSelected }
                    )
                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = EmpathTheme.shapes.small,
                        colors = CardDefaults.cardColors(
                            containerColor = EmpathTheme.colors.surfaceContainer,
                            contentColor = EmpathTheme.colors.onSurface,
                        ),
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = state.author.companyName,
                            style = EmpathTheme.typography.headlineLarge,
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.author.companyDescription,
                        style = EmpathTheme.typography.titleSmall,
                    )

                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.responsibilities,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { responsibilities ->
                            onEvent(
                                VacancyCreateEvent.ResponsibilitiesChange(responsibilities)
                            )
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        minLines = 5,
                        label = {
                            Text(
                                text = stringResource(Res.string.responsibilities),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.requirements,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { requirements ->
                            onEvent(
                                VacancyCreateEvent.RequirementsChange(requirements)
                            )
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        minLines = 5,
                        label = {
                            Text(
                                text = stringResource(Res.string.requirements),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    SelectedSkills(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(Res.string.selected_key_skills),
                        skills = state.newVacancy.skills,
                        onAddSkillClick = { onEvent(VacancyCreateEvent.AddKeySkillsClick) },
                        onSkillRemoveClick = { skill ->
                            onEvent(
                                VacancyCreateEvent.RemoveKeySkill(
                                    skill
                                )
                            )
                        },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.additionalDescription,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { description ->
                            onEvent(
                                VacancyCreateEvent.AdditionalDescriptionChange(description)
                            )
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        minLines = 2,
                        label = {
                            Text(
                                text = stringResource(Res.string.additional_description),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    SelectedSkills(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(Res.string.selected_additional_skills),
                        skills = state.newVacancy.additionalSkills,
                        onAddSkillClick = { onEvent(VacancyCreateEvent.AddAdditionalSkillsClick) },
                        onSkillRemoveClick = { skill ->
                            onEvent(
                                VacancyCreateEvent.RemoveAdditionalSkill(
                                    skill
                                )
                            )
                        },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.newVacancy.email,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { email -> onEvent(VacancyCreateEvent.EmailChange(email)) },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 2,
                        label = {
                            Text(
                                text = stringResource(Res.string.email),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                        leadingIcon = {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Res.drawable.ic_alternate_email),
                                    contentDescription = null,
                                )
                            }
                        },
                    )

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { onEvent(VacancyCreateEvent.CreateVacancyClick) },
                        shape = EmpathTheme.shapes.small,
                        enabled = state.newVacancy.isFilled(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.primary,
                            contentColor = EmpathTheme.colors.onPrimary,
                        ),
                    ) {
                        Text(
                            text = stringResource(Res.string.create_vacancy),
                            style = EmpathTheme.typography.labelLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is VacancyCreateState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is VacancyCreateState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message,
                )
            }

            is VacancyCreateState.Initial -> Unit
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SelectedSkills(
    modifier: Modifier = Modifier,
    title: String,
    skills: List<SkillUi>,
    onSkillRemoveClick: (SkillUi) -> Unit,
    onAddSkillClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            color = EmpathTheme.colors.onSurface,
        )
        if (skills.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                skills.forEach { skill ->
                    Box(
                        modifier = Modifier
                            .clip(EmpathTheme.shapes.small)
                            .noRippleClickable { onSkillRemoveClick(skill) }
                            .background(EmpathTheme.colors.primaryContainer)
                            .padding(vertical = 6.dp, horizontal = 16.dp),
                    ) {
                        Text(
                            text = skill.name,
                            style = EmpathTheme.typography.labelMedium,
                            color = EmpathTheme.colors.onPrimaryContainer,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                contentColor = EmpathTheme.colors.primary,
                containerColor = EmpathTheme.colors.surface,
            ),
            shape = EmpathTheme.shapes.small,
            border = BorderStroke(
                width = 1.dp,
                color = EmpathTheme.colors.outlineVariant,
            ),
            onClick = onAddSkillClick,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(Res.string.add_skill),
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }
    }
}


