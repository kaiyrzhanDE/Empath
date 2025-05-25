@file:OptIn(ExperimentalUuidApi::class)

package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import kaiyrzhan.de.empath.core.ui.files.toString
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.components.ThousandSeparatorTransformation
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerDialog
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendRequiredMarker
import kaiyrzhan.de.empath.core.ui.files.FileExtensions
import kaiyrzhan.de.empath.core.ui.files.rememberFilePicker
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.navigation.BackHandler
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.core.utils.logger.ifNull
import kaiyrzhan.de.empath.core.utils.toIntLimited
import kaiyrzhan.de.empath.features.vacancies.ui.components.FiltersCard
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.component.CvPickerField
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.component.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.component.WorkExperienceCard
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditState
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialog
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.SelectedSkills
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.components.FiltersCard
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyFilterState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi

@Composable
internal fun CvEditScreen(
    modifier: Modifier = Modifier,
    component: CvEditComponent,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val state = component.state.collectAsState()
    val workSchedulesState = component.workSchedulesState.collectAsState()
    val workFormatsState = component.workFormatsState.collectAsState()
    val employmentTypesState = component.employmentTypesState.collectAsState()

    BackHandler(component.backHandler) {
        component.onEvent(CvEditEvent.BackClick)
    }

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

    val datePickerSlot by component.datePicker.subscribeAsState()
    datePickerSlot.child?.instance?.also { datePickerComponent ->
        DatePickerDialog(
            component = datePickerComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is CvEditAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    CvEditScreen(
        modifier = modifier,
        state = state.value,
        workFormatsState = workFormatsState.value,
        employmentTypesState = employmentTypesState.value,
        workSchedulesState = workSchedulesState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun CvEditScreen(
    modifier: Modifier = Modifier,
    state: CvEditState,
    employmentTypesState: VacancyFilterState,
    workFormatsState: VacancyFilterState,
    workSchedulesState: VacancyFilterState,
    onEvent: (CvEditEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    val singleFilePicker = rememberFilePicker(
        title = stringResource(Res.string.select_cv),
        extensions = listOf(
            FileExtensions.PDF,
            FileExtensions.DOCX,
        ),
    ) { selectedFile ->
        onEvent(CvEditEvent.CvFileAdd(selectedFile))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is CvEditState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .screenHorizontalPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.cv.title,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { title -> onEvent(CvEditEvent.TitleChange(title)) },
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
                        value = state.cv.email,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { email -> onEvent(CvEditEvent.EmailChange(email)) },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 2,
                        label = {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(Res.string.email))
                                    appendRequiredMarker()
                                },
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
                                value = state.cv.salary.from?.toString().orEmpty(),
                                shape = EmpathTheme.shapes.small,
                                onValueChange = { from ->
                                    onEvent(
                                        CvEditEvent.SalaryFromChange(
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
                                        text = buildAnnotatedString {
                                            append(stringResource(Res.string.from))
                                            appendRequiredMarker()
                                        },
                                        style = EmpathTheme.typography.bodyLarge,
                                    )
                                },
                            )

                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.cv.salary.to?.toString().orEmpty(),
                                shape = EmpathTheme.shapes.small,
                                onValueChange = { to ->
                                    onEvent(
                                        CvEditEvent.SalaryToChange(
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

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.cv.address,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { address ->
                            onEvent(CvEditEvent.AddressChange(address))
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        maxLines = 2,
                        label = {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(Res.string.address))
                                    appendRequiredMarker()
                                },
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

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.cv.aboutMe,
                        shape = EmpathTheme.shapes.small,
                        onValueChange = { aboutMe ->
                            onEvent(CvEditEvent.AboutMeChange(aboutMe))
                        },
                        textStyle = EmpathTheme.typography.bodyLarge,
                        minLines = 3,
                        label = {
                            Text(
                                text = stringResource(Res.string.about_me),
                                style = EmpathTheme.typography.bodyLarge,
                            )
                        },
                    )

                    FiltersCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = employmentTypesState,
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.select_employments_types))
                            appendRequiredMarker()
                        },
                        leadingPainter = painterResource(Res.drawable.ic_schedule),
                        onSelect = { employmentType ->
                            onEvent(CvEditEvent.EmploymentTypeSelect(employmentType))
                        },
                        isSelected = { employmentType -> employmentType in state.cv.selectedEmploymentTypes },
                        onReload = {
                            onEvent(CvEditEvent.LoadEmploymentTypes)
                        },
                        anySelected = { state.cv.selectedEmploymentTypes.isNotEmpty() },
                    )

                    FiltersCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = workFormatsState,
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.select_work_formats))
                            appendRequiredMarker()
                        },
                        leadingPainter = painterResource(Res.drawable.ic_domain),
                        onSelect = { workFormat ->
                            onEvent(CvEditEvent.WorkFormatSelect(workFormat))
                        },
                        isSelected = { workFormats -> workFormats in state.cv.selectedWorkFormats },
                        onReload = {
                            onEvent(CvEditEvent.LoadWorkFormats)
                        },
                        anySelected = { state.cv.selectedWorkFormats.isNotEmpty() },
                    )

                    FiltersCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = workSchedulesState,
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.select_work_schedules))
                            appendRequiredMarker()
                        },
                        leadingPainter = painterResource(Res.drawable.ic_calendar_today),
                        onSelect = { workSchedule ->
                            onEvent(CvEditEvent.WorkScheduleSelect(workSchedule))
                        },
                        isSelected = { workSchedule -> workSchedule in state.cv.selectedWorkSchedules },
                        onReload = {
                            onEvent(CvEditEvent.LoadWorkSchedules)
                        },
                        anySelected = { state.cv.selectedWorkSchedules.isNotEmpty() },
                    )

                    FiltersCard<EducationUi>(
                        modifier = Modifier.fillMaxWidth(),
                        filters = state.cv.educations,
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.select_education))
                            appendRequiredMarker()
                        },
                        leadingPainter = painterResource(Res.drawable.ic_school),
                        anySelected = { educations -> educations.any { it.isSelected } },
                        onSelect = { education ->
                            onEvent(CvEditEvent.EducationSelect(education))
                        },
                        label = { education -> stringResource(education.type.res) },
                        isSelected = { education -> education.isSelected }
                    )

                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.work_experience))
                            appendColon()
                        },
                        style = EmpathTheme.typography.labelLarge,
                    )
                    state.cv.workExperiences
                        .forEachIndexed { index, workExperience ->
                            WorkExperienceCard(
                                modifier = Modifier.fillMaxWidth(),
                                position = index,
                                workExperience = workExperience,
                                onEvent = onEvent,
                            )
                        }

                    Column(
                        modifier = modifier
                            .clip(EmpathTheme.shapes.small)
                            .border(
                                width = 1.dp,
                                color = EmpathTheme.colors.outlineVariant,
                                shape = EmpathTheme.shapes.small,
                            )
                            .background(EmpathTheme.colors.surface)
                            .clickable { onEvent(CvEditEvent.WorkExperienceAdd) }
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(Res.string.add_work_experience),
                            style = EmpathTheme.typography.labelLarge,
                            color = EmpathTheme.colors.primary,
                        )
                    }

                    SelectedSkills(
                        modifier = Modifier.fillMaxWidth(),
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.selected_key_skills))
                            appendRequiredMarker()
                        },
                        skills = state.cv.skills,
                        onAddSkillClick = { onEvent(CvEditEvent.AddKeySkillsClick) },
                        onSkillRemoveClick = { skill ->
                            onEvent(CvEditEvent.RemoveKeySkill(skill))
                        },
                    )

                    SelectedSkills(
                        modifier = Modifier.fillMaxWidth(),
                        title = buildAnnotatedString {
                            append(stringResource(Res.string.selected_additional_skills))
                        },
                        skills = state.cv.additionalSkills,
                        onAddSkillClick = { onEvent(CvEditEvent.AddAdditionalSkillsClick) },
                        onSkillRemoveClick = { skill ->
                            onEvent(CvEditEvent.RemoveAdditionalSkill(skill))
                        },
                    )

                    CvPickerField(
                        modifier = Modifier.fillMaxWidth(),
                        selected = state.cv.cvFile?.platformFile.toString(),
                        isLoading = state.cv.cvFile?.isLoading.ifNull { false },
                        onClick = { singleFilePicker.launch() },
                    )

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { onEvent(CvEditEvent.CvEditClick) },
                        shape = EmpathTheme.shapes.small,
                        enabled = state.cv.isChanged(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmpathTheme.colors.primary,
                            contentColor = EmpathTheme.colors.onPrimary,
                        ),
                    ) {
                        Text(
                            text = stringResource(Res.string.edit_cv),
                            style = EmpathTheme.typography.labelLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is CvEditState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                )
            }

            is CvEditState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = { onEvent(CvEditEvent.LoadCv) },
                )
            }

            is CvEditState.Initial -> Unit
        }

    }
}