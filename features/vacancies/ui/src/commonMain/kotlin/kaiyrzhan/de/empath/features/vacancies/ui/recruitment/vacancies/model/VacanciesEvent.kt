package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseUi

internal sealed interface VacanciesEvent {
    data class VacanciesSearch(val query: String) : VacanciesEvent
    data object VacanciesFiltersClick : VacanciesEvent
    data class TabChange(val index: Int) : VacanciesEvent
    data class VacancyEditClick(val id: String) : VacanciesEvent
    data class VacancyHideClick(val id: String) : VacanciesEvent
    data object VacancyCreateClick : VacanciesEvent
    data class VacancyDetailClick(val id: String) : VacanciesEvent

    data class ResponseAccept(val response: ResponseUi) : VacanciesEvent
    data class ResponseReject(val response: ResponseUi) : VacanciesEvent
    data class ResponseCvClick(val response: ResponseUi) : VacanciesEvent
}