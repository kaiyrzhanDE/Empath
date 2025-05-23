package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate

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
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkFormatsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkSchedulesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.CreateVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.RealSkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
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

internal class RealVacancyCreateComponent(
    componentContext: ComponentContext,
    private val author: AuthorUi,
    private val onBackClick: () -> Unit,
    private val onVacancyCreateClick: () -> Unit,
) : BaseComponent(componentContext), VacancyCreateComponent {

    private val getWorkFormatsUseCase: GetWorkFormatsUseCase = get()
    private val getWorkSchedulesUseCase: GetWorkSchedulesUseCase = get()
    private val getEmploymentTypesUseCase: GetEmploymentTypesUseCase = get()
    private val createVacancyUseCase: CreateVacancyUseCase by inject()

    override val state = MutableStateFlow<VacancyCreateState>(
        VacancyCreateState.default(author)
    )

    private val vacancyFilterDefault = VacancyFilterState.Initial
    override val employmentTypesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workFormatsState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workSchedulesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)

    private val _action = Channel<VacancyCreateAction>(capacity = Channel.BUFFERED)
    override val action: Flow<VacancyCreateAction> = _action.receiveAsFlow()

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
        loadWorkSchedules()
        loadWorkFormats()
        loadEmploymentTypes()
    }

    override fun onEvent(event: VacancyCreateEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacancyCreateEvent.BackClick -> backClick()
            is VacancyCreateEvent.VacancyCreateClick -> onVacancyCreateClick()
            is VacancyCreateEvent.EducationSelect -> selectEducation(event.education)
            is VacancyCreateEvent.WorkExperienceSelect -> selectWorkExperience(event.workExperience)
            is VacancyCreateEvent.WorkFormatSelect -> selectWorkFormats(event.workFormat)
            is VacancyCreateEvent.WorkScheduleSelect -> selectWorkSchedule(event.workSchedule)
            is VacancyCreateEvent.EmploymentTypeSelect -> selectEmploymentType(event.employmentType)
            is VacancyCreateEvent.KeySkillsAdd -> addKeySkills(event.skills)
            is VacancyCreateEvent.AdditionalSkillsAdd -> addAdditionalSkills(event.skills)
            is VacancyCreateEvent.TitleChange -> changeTitle(event.title)
            is VacancyCreateEvent.ResponsibilitiesChange -> changeResponsibilities(event.responsibilities)
            is VacancyCreateEvent.RequirementsChange -> changeRequirements(event.requirements)
            is VacancyCreateEvent.AddressChange -> changeAddress(event.address)
            is VacancyCreateEvent.EmailChange -> changeEmail(event.email)
            is VacancyCreateEvent.SalaryFromChange -> changeSalaryFrom(event.salaryFrom)
            is VacancyCreateEvent.SalaryToChange -> changeSalaryTo(event.salaryTo)
            is VacancyCreateEvent.RemoveKeySkill -> removeKeySkill(event.skill)
            is VacancyCreateEvent.RemoveAdditionalSkill -> removeAdditionalSkill(event.skill)
            is VacancyCreateEvent.ChangeVisibility -> changeVisibility()
            is VacancyCreateEvent.AddKeySkillsClick -> showKeySkillsDialog()
            is VacancyCreateEvent.AddAdditionalSkillsClick -> showAdditionalSkillsDialog()
            is VacancyCreateEvent.CreateVacancyClick -> createVacancy()
            is VacancyCreateEvent.AdditionalDescriptionChange -> changeAdditionalDescription(event.description)
            is VacancyCreateEvent.LoadWorkFormats -> loadWorkFormats()
            is VacancyCreateEvent.LoadWorkSchedules -> loadWorkSchedules()
            is VacancyCreateEvent.LoadEmploymentTypes -> loadEmploymentTypes()
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
        check(currentState is VacancyCreateState.Success)
        when (currentState.newVacancy.isChanged()) {
            true -> coroutineScope.launch {
                showMessageDialog(
                    title = getString(Res.string.abort_vacancy_create_title),
                    description = getString(Res.string.abort_vacancy_create_description),
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
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    educations = currentState.newVacancy.educations.map { education ->
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
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    workExperiences = currentState.newVacancy.workExperiences.map { workExperience ->
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
            check(currentState is VacancyCreateState.Success)
            val isSelected = selectedWorkFormat in currentState.newVacancy.selectedWorkFormats
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    selectedWorkFormats = if (isSelected) {
                        currentState.newVacancy.selectedWorkFormats - selectedWorkFormat
                    } else {
                        currentState.newVacancy.selectedWorkFormats + selectedWorkFormat
                    }
                )
            )
        }
    }

    private fun selectWorkSchedule(selectedWorkSchedule: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            val isSelected = selectedWorkSchedule in currentState.newVacancy.selectedWorkSchedules
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    selectedWorkSchedules = if (isSelected) {
                        currentState.newVacancy.selectedWorkSchedules - selectedWorkSchedule
                    } else {
                        currentState.newVacancy.selectedWorkSchedules + selectedWorkSchedule
                    }
                )
            )
        }
    }

    private fun selectEmploymentType(selectedEmploymentType: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            val isSelected = selectedEmploymentType in currentState.newVacancy.selectedEmploymentTypes
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    selectedEmploymentTypes = if (isSelected) {
                        currentState.newVacancy.selectedEmploymentTypes - selectedEmploymentType
                    } else {
                        currentState.newVacancy.selectedEmploymentTypes + selectedEmploymentType
                    }
                )
            )
        }
    }

    private fun addKeySkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    skills = skills,
                ),
            )
        }
    }

    private fun addAdditionalSkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    additionalSkills = skills,
                ),
            )
        }
    }

    private fun changeTitle(title: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changeResponsibilities(responsibilities: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    responsibilities = responsibilities,
                ),
            )
        }
    }

    private fun changeRequirements(requirements: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    requirements = requirements,
                ),
            )
        }
    }

    private fun changeAddress(address: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    address = address,
                ),
            )
        }
    }

    private fun changeAdditionalDescription(description: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    additionalDescription = description,
                ),
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    email = email,
                ),
            )
        }
    }

    private fun changeSalaryFrom(salaryFrom: Int?) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    salaryFrom = salaryFrom,
                ),
            )
        }
    }

    private fun changeSalaryTo(salaryTo: Int?) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    salaryTo = salaryTo,
                ),
            )
        }
    }

    private fun changeVisibility() {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    isVisible = currentState.newVacancy.isVisible.not(),
                ),
            )
        }
    }

    private fun removeKeySkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    skills = currentState.newVacancy.skills - skill,
                ),
            )
        }
    }

    private fun removeAdditionalSkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    additionalSkills = currentState.newVacancy.additionalSkills - skill,
                ),
            )
        }
    }

    private fun showKeySkillsDialog() {
        val currentState = state.value
        check(currentState is VacancyCreateState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.newVacancy.skills,
                isKeySkills = true,
            ),
        )
    }

    private fun showAdditionalSkillsDialog() {
        val currentState = state.value
        check(currentState is VacancyCreateState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.newVacancy.additionalSkills,
                isKeySkills = false,
            ),
        )
    }

    private fun createVacancy() {
        val currentState = state.value
        check(currentState is VacancyCreateState.Success)
        state.update { VacancyCreateState.Loading }
        coroutineScope.launch {
            createVacancyUseCase(
                vacancy = currentState.newVacancy.toDomain(),
            ).onSuccess {
                _action.send(
                    VacancyCreateAction.ShowSnackbar(
                        message = getString(Res.string.vacancy_created_successfully),
                    )
                )
                onVacancyCreateClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            VacancyCreateAction.ShowSnackbar(
                                message = error.toString(),
                            )
                        )
                    }
                }
            }
        }
    }
}