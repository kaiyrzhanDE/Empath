package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy

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
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.RealSkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
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
        coroutineScope.launch {
            getWorkSchedulesUseCase().onSuccess { workSchedules ->
                state.update { currentState ->
                    check(currentState is VacancyCreateState.Success)
                    currentState.copy(
                        newVacancy = currentState.newVacancy.copy(
                            workSchedules = workSchedules.map { workSchedule -> workSchedule.toUi() },
                        )
                    )
                }
            }.onFailure {
                _action.send(
                    VacancyCreateAction.ShowSnackbar(
                        message = getString(Res.string.unknown_error),
                    )
                )
            }
        }
    }

    private fun loadWorkFormats() {
        coroutineScope.launch {
            getWorkFormatsUseCase().onSuccess { workFormats ->
                state.update { currentState ->
                    check(currentState is VacancyCreateState.Success)
                    currentState.copy(
                        newVacancy = currentState.newVacancy.copy(
                            workFormats = workFormats.map { workFormat -> workFormat.toUi() },
                        )
                    )
                }
            }.onFailure {
                _action.send(
                    VacancyCreateAction.ShowSnackbar(
                        message = getString(Res.string.unknown_error),
                    )
                )
            }
        }
    }


    private fun loadEmploymentTypes() {
        coroutineScope.launch {
            getEmploymentTypesUseCase().onSuccess { employmentTypes ->
                state.update { currentState ->
                    check(currentState is VacancyCreateState.Success)
                    currentState.copy(
                        newVacancy = currentState.newVacancy.copy(
                            employmentTypes = employmentTypes.map { employmentType -> employmentType.toUi() },
                        )
                    )
                }
            }.onFailure {
                _action.send(
                    VacancyCreateAction.ShowSnackbar(
                        message = getString(Res.string.unknown_error),
                    )
                )
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
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    workFormats = currentState.newVacancy.workFormats.map { workFormat ->
                        if (workFormat.id == selectedWorkFormat.id) {
                            workFormat.copy(
                                isSelected = !workFormat.isSelected
                            )
                        } else {
                            workFormat
                        }
                    }
                )
            )
        }
    }

    private fun selectWorkSchedule(selectedWorkSchedule: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    workSchedules = currentState.newVacancy.workSchedules.map { workSchedule ->
                        if (workSchedule.id == selectedWorkSchedule.id) {
                            workSchedule.copy(
                                isSelected = !workSchedule.isSelected
                            )
                        } else {
                            workSchedule
                        }
                    }
                )
            )
        }
    }

    private fun selectEmploymentType(selectedEmploymentType: SkillUi) {
        state.update { currentState ->
            check(currentState is VacancyCreateState.Success)
            currentState.copy(
                newVacancy = currentState.newVacancy.copy(
                    employmentTypes = currentState.newVacancy.employmentTypes.map { employmentType ->
                        if (employmentType.id == selectedEmploymentType.id) {
                            employmentType.copy(
                                isSelected = !employmentType.isSelected
                            )
                        } else {
                            employmentType
                        }
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