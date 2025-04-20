package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetEmploymentTypesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetVacancyDetailUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkFormatsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkSchedulesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.EditVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.RealSkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyFilterState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealVacancyEditComponent(
    componentContext: ComponentContext,
    private val vacancyId: String,
    private val onBackClick: () -> Unit,
    private val onVacancyEdited: () -> Unit,
) : BaseComponent(componentContext), VacancyEditComponent {

    private val getWorkFormatsUseCase: GetWorkFormatsUseCase = get()
    private val getVacancyDetailUseCase: GetVacancyDetailUseCase = get()
    private val getWorkSchedulesUseCase: GetWorkSchedulesUseCase = get()
    private val getEmploymentTypesUseCase: GetEmploymentTypesUseCase = get()
    private val editVacancyUseCase: EditVacancyUseCase by inject()

    override val state = MutableStateFlow<VacancyEditState>(
        VacancyEditState.default()
    )

    private val vacancyFilterDefault = VacancyFilterState.Initial
    override val employmentTypesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workFormatsState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workSchedulesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)

    private val _action = Channel<VacancyEditAction>(capacity = Channel.BUFFERED)
    override val action: Flow<VacancyEditAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    private val skillsDialogNavigation = SlotNavigation<SkillsState>()
    override val skillsDialog: Value<ChildSlot<*, SkillsDialogComponent>> = childSlot(
        source = skillsDialogNavigation,
        key = SkillsDialogComponent.DEFAULT_KEY,
        serializer = SkillsState.serializer(),
        childFactory = ::createSkillsDialog,
    )

    init {
        loadVacancy(vacancyId)
        loadEmploymentTypes()
        loadWorkSchedules()
        loadWorkFormats()
    }

    override fun onEvent(event: VacancyEditEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacancyEditEvent.BackClick -> backClick()
            is VacancyEditEvent.EducationSelect -> selectEducation(event.education)
            is VacancyEditEvent.WorkExperienceSelect -> selectWorkExperience(event.workExperience)
            is VacancyEditEvent.WorkFormatSelect -> selectWorkFormats(event.workFormat)
            is VacancyEditEvent.WorkScheduleSelect -> selectWorkSchedule(event.workSchedule)
            is VacancyEditEvent.EmploymentTypeSelect -> selectEmploymentType(event.employmentType)
            is VacancyEditEvent.KeySkillsAdd -> addKeySkills(event.skills)
            is VacancyEditEvent.AdditionalSkillsAdd -> addAdditionalSkills(event.skills)
            is VacancyEditEvent.TitleChange -> changeTitle(event.title)
            is VacancyEditEvent.ResponsibilitiesChange -> changeResponsibilities(event.responsibilities)
            is VacancyEditEvent.RequirementsChange -> changeRequirements(event.requirements)
            is VacancyEditEvent.AddressChange -> changeAddress(event.address)
            is VacancyEditEvent.EmailChange -> changeEmail(event.email)
            is VacancyEditEvent.SalaryFromChange -> changeSalaryFrom(event.salaryFrom)
            is VacancyEditEvent.SalaryToChange -> changeSalaryTo(event.salaryTo)
            is VacancyEditEvent.KeySkillRemove -> removeKeySkill(event.skill)
            is VacancyEditEvent.AdditionalSkillRemove -> removeAdditionalSkill(event.skill)
            is VacancyEditEvent.VisibilityChange -> changeVisibility()
            is VacancyEditEvent.KeySkillsAddClick -> showKeySkillsDialog()
            is VacancyEditEvent.AdditionalSkillsAddClick -> showAdditionalSkillsDialog()
            is VacancyEditEvent.VacancyEditClick -> editVacancy(vacancyId)
            is VacancyEditEvent.AdditionalDescriptionChange -> changeAdditionalDescription(event.description)
            is VacancyEditEvent.LoadVacancy -> loadVacancy(vacancyId)
            is VacancyEditEvent.LoadWorkFormats -> loadWorkFormats()
            is VacancyEditEvent.LoadWorkSchedules -> loadWorkSchedules()
            is VacancyEditEvent.LoadEmploymentTypes -> loadEmploymentTypes()
        }
    }

    private fun createMessageDialog(
        state: MessageDialogState,
        childComponentContext: ComponentContext,
    ): MessageDialogComponent {
        return RealMessageDialogComponent(
            componentContext = childComponentContext,
            messageDialogState = state,
        )
    }

    private fun showMessageDialog(
        title: String,
        description: String,
        dismissActionConfig: DialogActionConfig,
        confirmActionConfig: DialogActionConfig? = null,
        onDismissClick: (() -> Unit)? = null,
        onConfirmClick: (() -> Unit)? = null,
    ) {
        messageDialogNavigation.activate(
            configuration = MessageDialogState(
                title = title,
                description = description,
                dismissActionConfig = dismissActionConfig,
                onDismissClick = {
                    messageDialogNavigation
                        .dismiss()
                        .also { onDismissClick?.invoke() }
                },
                confirmActionConfig = confirmActionConfig,
                onConfirmClick = {
                    messageDialogNavigation
                        .dismiss()
                        .also { onConfirmClick?.invoke() }
                },
            ),
        )
    }

    private fun createSkillsDialog(
        state: SkillsState,
        childComponentContext: ComponentContext,
    ): SkillsDialogComponent {
        return RealSkillsDialogComponent(
            componentContext = childComponentContext,
            skillsDialogState = state,
            onDismissClick = { selectedSkills, isKeySkills ->
                skillsDialogNavigation
                    .dismiss()
                    .also {
                        when (isKeySkills) {
                            true -> addKeySkills(selectedSkills)
                            false -> addAdditionalSkills(selectedSkills)
                        }
                    }
            },
        )
    }

    private fun backClick() {
        val currentState = state.value
        check(currentState is VacancyEditState.Success)
        when (currentState.editableVacancy != currentState.originalVacancy) {
            true -> coroutineScope.launch {
                showMessageDialog(
                    title = getString(Res.string.abort_vacancy_edit_title),
                    description = getString(Res.string.abort_vacancy_edit_description),
                    dismissActionConfig = DialogActionConfig(
                        text = getString(Res.string.close),
                    ),
                    confirmActionConfig = DialogActionConfig(
                        text = getString(Res.string.stay_here),
                        isPrimary = true,
                    ),
                    onDismissClick = onBackClick,
                )
            }

            false -> onBackClick()
        }
    }


    private fun loadWorkSchedules() {
        if (workSchedulesState.value is VacancyFilterState.Success) return
        workSchedulesState.update { VacancyFilterState.Loading }
        coroutineScope.launch {
            getWorkSchedulesUseCase().onSuccess { workSchedules ->
                workSchedulesState.update {
                    VacancyFilterState.Success(
                        filters = workSchedules.map { workSchedule ->
                            workSchedule.toUi()
                        },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        workSchedulesState.update {
                            VacancyFilterState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadWorkFormats() {
        if (workFormatsState.value is VacancyFilterState.Success) return
        workFormatsState.update { VacancyFilterState.Loading }
        coroutineScope.launch {
            getWorkFormatsUseCase().onSuccess { workFormats ->
                workFormatsState.update {
                    VacancyFilterState.Success(
                        filters = workFormats.map { workFormat ->
                            workFormat.toUi()
                        },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        workFormatsState.update {
                            VacancyFilterState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }


    private fun loadEmploymentTypes() {
        if (employmentTypesState.value is VacancyFilterState.Success) return
        employmentTypesState.update { VacancyFilterState.Loading }
        coroutineScope.launch {
            getEmploymentTypesUseCase().onSuccess { employmentTypes ->
                employmentTypesState.update {
                    VacancyFilterState.Success(
                        filters = employmentTypes.map { employmentType ->
                            employmentType.toUi()
                        },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        employmentTypesState.update {
                            VacancyFilterState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun selectEducation(selectedEducation: EducationUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    educations = currentState.editableVacancy.educations.map { education ->
                        education.copy(
                            isSelected = education == selectedEducation
                        )
                    }
                ),
            )
        }
    }

    private fun selectWorkExperience(selectedWorkExperience: WorkExperienceUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    workExperiences = currentState.editableVacancy.workExperiences.map { workExperience ->
                        workExperience.copy(
                            isSelected = workExperience == selectedWorkExperience
                        )
                    }
                )
            )
        }
    }

    private fun selectWorkFormats(selectedWorkFormat: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            val isSelected = selectedWorkFormat in currentState.editableVacancy.selectedWorkFormats
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    selectedWorkFormats = if (isSelected) {
                        currentState.editableVacancy.selectedWorkFormats - selectedWorkFormat
                    } else {
                        currentState.editableVacancy.selectedWorkFormats + selectedWorkFormat
                    },
                )
            )
        }
    }

    private fun selectWorkSchedule(selectedWorkSchedule: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            val isSelected = selectedWorkSchedule in currentState.editableVacancy.selectedWorkSchedules
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    selectedWorkSchedules = if (isSelected) {
                        currentState.editableVacancy.selectedWorkSchedules - selectedWorkSchedule
                    } else {
                        currentState.editableVacancy.selectedWorkSchedules + selectedWorkSchedule
                    },
                )
            )
        }
    }

    private fun selectEmploymentType(selectedEmploymentType: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            val isSelected = selectedEmploymentType in currentState.editableVacancy.selectedEmploymentTypes
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    selectedEmploymentTypes = if (isSelected) {
                        currentState.editableVacancy.selectedEmploymentTypes - selectedEmploymentType
                    } else {
                        currentState.editableVacancy.selectedEmploymentTypes + selectedEmploymentType
                    },
                )
            )
        }
    }

    private fun addKeySkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    skills = skills,
                ),
            )
        }
    }

    private fun addAdditionalSkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    additionalSkills = skills,
                ),
            )
        }
    }

    private fun changeTitle(title: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changeResponsibilities(responsibilities: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    responsibilities = responsibilities,
                ),
            )
        }
    }

    private fun changeRequirements(requirements: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    requirements = requirements,
                ),
            )
        }
    }

    private fun changeAddress(address: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    address = address,
                ),
            )
        }
    }

    private fun changeAdditionalDescription(description: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    additionalDescription = description,
                ),
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    email = email,
                ),
            )
        }
    }

    private fun changeSalaryFrom(salaryFrom: Int?) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    salaryFrom = salaryFrom,
                ),
            )
        }
    }

    private fun changeSalaryTo(salaryTo: Int?) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    salaryTo = salaryTo,
                ),
            )
        }
    }

    private fun changeVisibility() {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    isVisible = currentState.editableVacancy.isVisible.not(),
                ),
            )
        }
    }

    private fun removeKeySkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    skills = currentState.editableVacancy.skills - skill,
                ),
            )
        }
    }

    private fun removeAdditionalSkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyEditState.Success)
            currentState.copy(
                editableVacancy = currentState.editableVacancy.copy(
                    additionalSkills = currentState.editableVacancy.additionalSkills - skill,
                ),
            )
        }
    }

    private fun showKeySkillsDialog() {
        val currentState = state.value
        check(currentState is VacancyEditState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.editableVacancy.skills,
                isKeySkills = true,
            ),
        )
    }

    private fun showAdditionalSkillsDialog() {
        val currentState = state.value
        check(currentState is VacancyEditState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.editableVacancy.additionalSkills,
                isKeySkills = false,
            ),
        )
    }

    private fun loadVacancy(
        vacancyId: String,
    ) {
        state.update { VacancyEditState.Loading }
        coroutineScope.launch {
            getVacancyDetailUseCase(vacancyId).onSuccess { vacancyDetail ->
                state.update {
                    VacancyEditState.Success(
                        originalVacancy = vacancyDetail.toUi(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            VacancyEditState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun editVacancy(
        vacancyId: String,
    ) {
        val currentState = state.value
        check(currentState is VacancyEditState.Success)
        state.update { VacancyEditState.Loading }
        coroutineScope.launch {
            editVacancyUseCase(
                id = vacancyId,
                vacancy = currentState.editableVacancy.toDomain(),
            ).onSuccess {
                _action.send(
                    VacancyEditAction.ShowSnackbar(
                        message = getString(Res.string.vacancy_edited_successfully),
                    )
                )
                onVacancyEdited()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            VacancyEditAction.ShowSnackbar(
                                message = error.toString(),
                            )
                        )
                    }
                }
            }
        }
    }
}