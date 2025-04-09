package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi

internal sealed interface VacanciesEvent {
    data class VacanciesSearch(val query: String) : VacanciesEvent
    data object VacanciesFiltersClick : VacanciesEvent
    data object CvCreateClick : VacanciesEvent
    data class TabChange(val index: Int) : VacanciesEvent
    data class VacancyDetailClick(val vacancy: VacancyUi) : VacanciesEvent
    data class ResponseToVacancy(val vacancy: VacancyUi) : VacanciesEvent
}