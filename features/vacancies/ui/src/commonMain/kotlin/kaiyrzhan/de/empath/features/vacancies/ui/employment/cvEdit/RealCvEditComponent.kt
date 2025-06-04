@file:OptIn(ExperimentalUuidApi::class)

package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.readBytes
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.RealDatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.filestorage.domain.model.FileType
import kaiyrzhan.de.empath.features.filestorage.domain.model.StorageName
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.UpdateCvUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetCvUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetEmploymentTypesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkFormatsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetWorkSchedulesUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditState
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.FileUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toUi
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
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class RealCvEditComponent(
    componentContext: ComponentContext,
    private val cvId: String,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), CvEditComponent {

    private val getWorkFormatsUseCase: GetWorkFormatsUseCase = get()
    private val getWorkSchedulesUseCase: GetWorkSchedulesUseCase = get()
    private val getEmploymentTypesUseCase: GetEmploymentTypesUseCase = get()
    private val uploadFileUseCase: UploadFileUseCase by inject()
    private val updateCvUseCase: UpdateCvUseCase by inject()
    private val getCvUseCase: GetCvUseCase = get()

    override val state = MutableStateFlow<CvEditState>(CvEditState.default())

    private val vacancyFilterDefault = VacancyFilterState.Initial
    override val employmentTypesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workFormatsState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)
    override val workSchedulesState = MutableStateFlow<VacancyFilterState>(vacancyFilterDefault)

    private val _action = Channel<CvEditAction>(capacity = Channel.BUFFERED)
    override val action: Flow<CvEditAction> = _action.receiveAsFlow()

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

    private val datePickerNavigation = SlotNavigation<DatePickerDialogState>()
    override val datePicker: Value<ChildSlot<*, DatePickerComponent>> = childSlot(
        source = datePickerNavigation,
        key = DatePickerComponent.DEFAULT_KEY,
        serializer = DatePickerDialogState.serializer(),
        childFactory = ::createDatePicker,
    )

    init {
        loadWorkFormats()
        loadWorkSchedules()
        loadEmploymentTypes()
        loadCv(cvId)
    }


    override fun onEvent(event: CvEditEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is CvEditEvent.TitleChange -> changeTitle(event.title)
            is CvEditEvent.VisibilityChange -> changeVisibility()
            is CvEditEvent.SalaryFromChange -> changeSalaryFrom(event.salaryFrom)
            is CvEditEvent.SalaryToChange -> changeSalaryTo(event.salaryTo)
            is CvEditEvent.WorkFormatSelect -> selectWorkFormat(event.workFormat)
            is CvEditEvent.WorkScheduleSelect -> selectWorkSchedule(event.workSchedule)
            is CvEditEvent.EmploymentTypeSelect -> selectEmploymentType(event.employmentType)
            is CvEditEvent.EducationSelect -> selectEducation(event.education)
            is CvEditEvent.AddressChange -> changeAddress(event.address)
            is CvEditEvent.AboutMeChange -> changeAboutMe(event.aboutMe)
            is CvEditEvent.CvFileAdd -> addCvFile(event.file)
            is CvEditEvent.CvFileRemove -> removeCvFile()
            is CvEditEvent.EmailChange -> changeEmail(event.email)
            is CvEditEvent.LoadWorkFormats -> loadWorkFormats()
            is CvEditEvent.LoadWorkSchedules -> loadWorkSchedules()
            is CvEditEvent.LoadEmploymentTypes -> loadEmploymentTypes()
            is CvEditEvent.AddKeySkillsClick -> showKeySkillsDialog()
            is CvEditEvent.AddAdditionalSkillsClick -> showAdditionalSkillsDialog()
            is CvEditEvent.KeySkillsAdd -> addKeySkills(event.skills)
            is CvEditEvent.AdditionalSkillsAdd -> addAdditionalSkills(event.skills)
            is CvEditEvent.RemoveKeySkill -> removeKeySkill(event.skill)
            is CvEditEvent.RemoveAdditionalSkill -> removeAdditionalSkill(event.skill)
            is CvEditEvent.BackClick -> backClick()
            is CvEditEvent.CvEditClick -> editCv()
            is CvEditEvent.LoadCv -> loadCv(cvId)
            is CvEditEvent.WorkExperienceAdd -> addWorkExperience()
            is CvEditEvent.WorkExperienceRemove -> removeWorkExperience(event.id)
            is CvEditEvent.WorkExperienceEndDateChange -> changeWorkExperienceEndDate(event.id)
            is CvEditEvent.WorkExperienceStartDateChange ->
                changeWorkExperienceStartDate(event.id)

            is CvEditEvent.WorkExperienceTitleChange ->
                changeWorkExperienceTitle(event.id, event.title)

            is CvEditEvent.WorkExperienceCompanyChange ->
                changeWorkExperienceCompany(event.id, event.company)

            is CvEditEvent.WorkExperienceIsRelevantChange ->
                changeWorkExperienceIsRelevant(event.id)

            is CvEditEvent.WorkExperienceDescriptionChange ->
                changeWorkExperienceDescription(event.id, event.description)
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

    private fun createDatePicker(
        state: DatePickerDialogState,
        childComponentContext: ComponentContext,
    ): DatePickerComponent {
        return RealDatePickerComponent(
            componentContext = childComponentContext,
            dialogState = state,
        )
    }

    private fun showDatePicker(
        date: LocalDateTime?,
        onSelect: (date: LocalDateTime?) -> Unit,
    ) {
        coroutineScope.launch {
            datePickerNavigation.activate(
                configuration = DatePickerDialogState(
                    selectedDate = date,
                    dismissActionConfig = DialogActionConfig(
                        text = getString(Res.string.cancel),
                    ),
                    onDismissClick = datePickerNavigation::dismiss,
                    confirmActionConfig = DialogActionConfig(
                        text = getString(Res.string.okay),
                        isPrimary = true,
                    ),
                    onConfirmClick = { selectedDate ->
                        onSelect(selectedDate)
                        datePickerNavigation.dismiss()
                    },
                ),
            )
        }
    }


    private fun backClick() {
        val currentState = state.value
        when {
            currentState is CvEditState.Success && currentState.cv.isChanged() -> coroutineScope.launch {
                showMessageDialog(
                    title = getString(Res.string.abort_cv_edit_title),
                    description = getString(Res.string.abort_cv_edit_description),
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

            else -> onBackClick()
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

    private fun changeTitle(title: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changeAddress(address: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    address = address,
                ),
            )
        }
    }

    private fun changeAboutMe(aboutMe: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    aboutMe = aboutMe,
                ),
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    email = email,
                ),
            )
        }
    }

    private fun changeSalaryFrom(salaryFrom: Int?) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    salary = currentState.cv.salary.copy(
                        from = salaryFrom
                    )
                )
            )
        }
    }

    private fun changeSalaryTo(salaryTo: Int?) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    salary = currentState.cv.salary.copy(
                        to = salaryTo
                    )
                )
            )
        }
    }

    private fun changeVisibility() {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    isVisible = currentState.cv.isVisible.not(),
                )
            )
        }
    }

    private fun selectWorkFormat(selected: SkillUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            val isSelected = selected in currentState.cv.selectedWorkFormats
            currentState.copy(
                cv = currentState.cv.copy(
                    selectedWorkFormats = if (isSelected) {
                        currentState.cv.selectedWorkFormats - selected
                    } else {
                        currentState.cv.selectedWorkFormats + selected
                    },
                ),
            )
        }
    }

    private fun selectWorkSchedule(selected: SkillUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            val isSelected = selected in currentState.cv.selectedWorkSchedules
            currentState.copy(
                cv = currentState.cv.copy(
                    selectedWorkSchedules = if (isSelected) {
                        currentState.cv.selectedWorkSchedules - selected
                    } else {
                        currentState.cv.selectedWorkSchedules + selected
                    },
                ),
            )
        }
    }

    private fun selectEmploymentType(selected: SkillUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            val isSelected = selected in currentState.cv.selectedEmploymentTypes
            currentState.copy(
                cv = currentState.cv.copy(
                    selectedEmploymentTypes = if (isSelected) {
                        currentState.cv.selectedEmploymentTypes - selected
                    } else {
                        currentState.cv.selectedEmploymentTypes + selected
                    },
                ),
            )
        }
    }

    private fun selectEducation(selectedEducation: EducationUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    educations = currentState.cv.educations.map { education ->
                        education.copy(
                            isSelected = education == selectedEducation
                        )
                    },
                )
            )
        }
    }

    private fun addKeySkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    skills = skills,
                ),
            )
        }
    }

    private fun addAdditionalSkills(skills: List<SkillUi>) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    additionalSkills = skills,
                ),
            )
        }
    }

    private fun removeKeySkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    skills = currentState.cv.skills - skill,
                ),
            )
        }
    }

    private fun removeAdditionalSkill(skill: SkillUi) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    additionalSkills = currentState.cv.additionalSkills - skill,
                ),
            )
        }
    }

    private fun showKeySkillsDialog() {
        val currentState = state.value
        check(currentState is CvEditState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.cv.skills,
                isKeySkills = true,
            ),
        )
    }

    private fun showAdditionalSkillsDialog() {
        val currentState = state.value
        check(currentState is CvEditState.Success)
        skillsDialogNavigation.activate(
            configuration = SkillsState(
                originalSkills = currentState.cv.additionalSkills,
                isKeySkills = false,
            ),
        )
    }

    private fun addCvFile(file: PlatformFile) {
        coroutineScope.launch {
            state.update { currentState ->
                check(currentState is CvEditState.Success)
                currentState.copy(
                    cv = currentState.cv.copy(
                        cvFile = FileUi.create(
                            isLoading = true,
                            platformFile = file,
                        ),
                    ),
                )
            }

            uploadFileUseCase(
                fileType = FileType.FILES,
                storageName = StorageName.CV,
                image = file.readBytes(),
                imageType = file.extension,
            ).onSuccess { result ->
                state.update { currentState ->
                    check(currentState is CvEditState.Success)
                    currentState.copy(
                        cv = currentState.cv.copy(
                            cvFile = currentState.cv.cvFile?.copy(
                                url = result.url.addBaseUrl(),
                                isLoading = false,
                            ),
                        ),
                    )
                }
            }.onFailure { error ->
                state.update { currentState ->
                    check(currentState is CvEditState.Success)
                    currentState.copy(
                        cv = currentState.cv.copy(
                            cvFile = null,
                        ),
                    )
                }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(CvEditAction.ShowSnackbar(error.toString()))
                    }
                }
            }
        }
    }

    private fun removeCvFile() {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    cvFile = null,
                ),
            )
        }
    }

    private fun addWorkExperience() {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences + WorkExperienceUi.create()
                ),
            )
        }
    }

    private fun removeWorkExperience(id: Uuid) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences
                        .filter { workExperience -> workExperience.id != id },
                ),
            )
        }
    }

    private fun changeWorkExperienceTitle(id: Uuid, title: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences.map { workExperience ->
                        if (workExperience.id == id) {
                            workExperience.copy(
                                title = title,
                            )
                        } else {
                            workExperience
                        }
                    }
                )
            )
        }
    }

    private fun changeWorkExperienceDescription(id: Uuid, description: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences.map { workExperience ->
                        if (workExperience.id == id) {
                            workExperience.copy(
                                description = description,
                            )
                        } else {
                            workExperience
                        }
                    }
                )
            )
        }
    }

    private fun changeWorkExperienceIsRelevant(id: Uuid) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences.map { workExperience ->
                        if (workExperience.id == id) {
                            workExperience.copy(
                                isRelevant = workExperience.isRelevant.not(),
                            )
                        } else {
                            workExperience
                        }
                    }
                )
            )
        }
    }

    private fun changeWorkExperienceCompany(id: Uuid, companyName: String) {
        state.update { currentState ->
            check(currentState is CvEditState.Success)
            currentState.copy(
                cv = currentState.cv.copy(
                    workExperiences = currentState.cv.workExperiences.map { workExperience ->
                        if (workExperience.id == id) {
                            workExperience.copy(
                                companyName = companyName,
                            )
                        } else {
                            workExperience
                        }
                    }
                )
            )
        }
    }

    private fun changeWorkExperienceStartDate(id: Uuid) {
        val currentState = state.value
        check(currentState is CvEditState.Success)
        val selectedWorkExperience = currentState.cv.workExperiences
            .find { workExperience -> workExperience.id == id }
        if (selectedWorkExperience != null) {
            showDatePicker(
                date = selectedWorkExperience.startDate,
                onSelect = { date ->
                    state.update { currentState ->
                        check(currentState is CvEditState.Success)
                        currentState.copy(
                            cv = currentState.cv.copy(
                                workExperiences = currentState.cv.workExperiences.map { workExperience ->
                                    if (workExperience.id == id) {
                                        workExperience.copy(
                                            startDate = date,
                                        )
                                    } else {
                                        workExperience
                                    }
                                }
                            )
                        )
                    }
                }
            )
        }
    }

    private fun changeWorkExperienceEndDate(id: Uuid) {
        val currentState = state.value
        check(currentState is CvEditState.Success)
        val selectedWorkExperience = currentState.cv.workExperiences
            .find { workExperience -> workExperience.id == id }
        if (selectedWorkExperience != null) {
            showDatePicker(
                date = selectedWorkExperience.endDate,
                onSelect = { date ->
                    state.update { currentState ->
                        check(currentState is CvEditState.Success)
                        currentState.copy(
                            cv = currentState.cv.copy(
                                workExperiences = currentState.cv.workExperiences.map { workExperience ->
                                    if (workExperience.id == id) {
                                        workExperience.copy(
                                            endDate = date,
                                        )
                                    } else {
                                        workExperience
                                    }
                                }
                            )
                        )
                    }
                }
            )
        }
    }

    private fun loadCv(cvId: String) {
        state.update { CvEditState.Loading }
        coroutineScope.launch {
            getCvUseCase(
                cvId = cvId,
            ).onSuccess { cv ->
                state.update {
                    CvEditState.Success(
                        cv = cv.toUi(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            CvEditState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun editCv() {
        val currentState = state.value
        check(currentState is CvEditState.Success)
        state.update { CvEditState.Loading }
        coroutineScope.launch {
            updateCvUseCase(
                cvId = cvId,
                cv = currentState.cv.toDomain(),
            ).onSuccess {
                _action.send(
                    CvEditAction.ShowSnackbar(
                        message = getString(Res.string.cv_edited_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            CvEditAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}