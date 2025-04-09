package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model

internal sealed interface VacancyDetailEvent {
    data object ReloadVacancyDetail : VacancyDetailEvent
    data object BackClick : VacancyDetailEvent
    data object VacancyDeleteClick : VacancyDetailEvent
    data object VacancyEditClick : VacancyDetailEvent
    data object ResponseToVacancyClick : VacancyDetailEvent
}