package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model

internal sealed interface VacancyDetailAction {
    class ShowSnackbar(val message: String) : VacancyDetailAction
}