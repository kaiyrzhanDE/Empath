package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

internal sealed interface VacanciesAction {
    class ShowSnackbar(val message: String): VacanciesAction
}