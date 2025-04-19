package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkFormatUi

internal sealed interface VacancyFiltersEvent {
    data class QueryChange(val query: String) : VacancyFiltersEvent
    data class IncludeWordsChange(val includeWords: String) : VacancyFiltersEvent
    data class ExcludeWordsChange(val excludeWords: String) : VacancyFiltersEvent
    data class SalaryFromChange(val salaryFrom: Int?) : VacancyFiltersEvent
    data class SalaryToChange(val salaryTo: Int?) : VacancyFiltersEvent
    data class WorkExperienceSelect(val workExperience: WorkExperienceUi) : VacancyFiltersEvent
    data class WorkFormatSelect(val workFormat: WorkFormatUi) : VacancyFiltersEvent
    data class EducationSelect(val education: EducationUi) : VacancyFiltersEvent
    data object Apply : VacancyFiltersEvent
    data object Clear : VacancyFiltersEvent
    data object BackClick : VacancyFiltersEvent
}