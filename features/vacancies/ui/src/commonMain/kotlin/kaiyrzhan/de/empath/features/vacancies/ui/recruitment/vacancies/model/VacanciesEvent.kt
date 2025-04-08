package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

internal sealed interface VacanciesEvent {
    data class VacanciesSearch(val query: String) : VacanciesEvent
    data object VacanciesFiltersClick : VacanciesEvent
    data class TabChange(val index: Int) : VacanciesEvent
    data class VacancyEditClick(val id: String) : VacanciesEvent
    data class VacancyHideClick(val id: String) : VacanciesEvent
    data object VacancyCreateClick : VacanciesEvent
}