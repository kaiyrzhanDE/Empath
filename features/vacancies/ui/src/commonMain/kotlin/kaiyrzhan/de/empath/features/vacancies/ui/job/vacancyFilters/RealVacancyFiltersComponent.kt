package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersState
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkFormatUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelectedTypes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class RealVacancyFiltersComponent(
    componentContext: ComponentContext,
    private val vacancyFilters: VacancyFiltersUi,
    private val onBackClick: (isVacancyFiltersUpdated: Boolean, vacancyFilters: VacancyFiltersUi) -> Unit,
) : BaseComponent(componentContext), VacancyFiltersComponent {

    override val state = MutableStateFlow<VacancyFiltersState>(
        VacancyFiltersState.default(
            vacancyFilters = vacancyFilters,
        )
    )

    override fun onEvent(event: VacancyFiltersEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacancyFiltersEvent.Apply -> apply()
            is VacancyFiltersEvent.Clear -> clear()
            is VacancyFiltersEvent.ExcludeWordsChange -> changeExcludeWords(event.excludeWords)
            is VacancyFiltersEvent.IncludeWordsChange -> changeIncludeWords(event.includeWords)
            is VacancyFiltersEvent.QueryChange -> changeQuery(event.query)
            is VacancyFiltersEvent.SalaryFromChange -> changeSalaryFrom(event.salaryFrom)
            is VacancyFiltersEvent.SalaryToChange -> changeSalaryTo(event.salaryTo)
            is VacancyFiltersEvent.WorkExperienceSelect -> selectWorkExperience(event.workExperience)
            is VacancyFiltersEvent.WorkFormatSelect -> selectWorkFormat(event.workFormat)
            is VacancyFiltersEvent.EducationSelect -> selectEducation(event.education)
            is VacancyFiltersEvent.BackClick -> backClick()
        }
    }

    private fun backClick() {
        onBackClick(false, vacancyFilters)
    }

    private fun apply() {
        val currentState = state.value
        val vacancyFilters = VacancyFiltersUi(
            query = currentState.query,
            salaryTo = currentState.salaryTo,
            salaryFrom = currentState.salaryFrom,
            excludeWords = currentState.excludeWords,
            includeWords = currentState.includeWords,
            selectedEducationTypes = currentState.educations.getSelectedTypes(),
            selectedWorkFormatTypes = currentState.workFormats.getSelectedTypes(),
            selectedWorkExperienceTypes = currentState.workExperiences.getSelectedTypes(),
        )
        onBackClick(true, vacancyFilters)
    }

    private fun clear() {
        state.update {
            VacancyFiltersState.default(
                vacancyFilters = VacancyFiltersUi(),
            )
        }
    }

    private fun changeExcludeWords(excludeWords: String) {
        state.update { currentState ->
            currentState.copy(
                excludeWords = excludeWords,
            )
        }
    }

    private fun changeIncludeWords(includeWords: String) {
        state.update { currentState ->
            currentState.copy(
                includeWords = includeWords,
            )
        }
    }

    private fun changeQuery(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query,
            )
        }
    }

    private fun changeSalaryFrom(salaryFrom: Int?) {
        state.update { currentState ->
            currentState.copy(
                salaryFrom = salaryFrom,
            )
        }
    }

    private fun changeSalaryTo(salaryTo: Int?) {
        state.update { currentState ->
            currentState.copy(
                salaryTo = salaryTo,
            )
        }
    }

    private fun selectWorkExperience(selectedWorkExperience: WorkExperienceUi) {
        state.update { currentState ->
            currentState.copy(
                workExperiences = currentState.workExperiences.map { workExperience ->
                    if (workExperience == selectedWorkExperience) {
                        workExperience.copy(
                            isSelected = workExperience.isSelected.not(),
                        )
                    } else {
                        workExperience
                    }
                },
            )
        }
    }

    private fun selectEducation(selectedEducation: EducationUi) {
        state.update { currentState ->
            currentState.copy(
                educations = currentState.educations.map { education ->
                    if (education == selectedEducation) {
                        education.copy(
                            isSelected = education.isSelected.not(),
                        )
                    } else {
                        education
                    }
                },
            )
        }
    }

    private fun selectWorkFormat(selectedWorkFormat: WorkFormatUi) {
        state.update { currentState ->
            currentState.copy(
                workFormats = currentState.workFormats.map { workFormat ->
                    if (workFormat == selectedWorkFormat) {
                        workFormat.copy(
                            isSelected = workFormat.isSelected.not(),
                        )
                    } else {
                        workFormat
                    }
                },
            )
        }
    }
}