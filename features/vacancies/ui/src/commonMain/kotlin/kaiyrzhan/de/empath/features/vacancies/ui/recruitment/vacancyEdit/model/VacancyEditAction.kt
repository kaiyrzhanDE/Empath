package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model

internal sealed interface VacancyEditAction {
    class ShowSnackbar(val message: String) : VacancyEditAction
}