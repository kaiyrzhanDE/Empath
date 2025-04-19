package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model

internal sealed interface VacancyCreateAction {
    class ShowSnackbar(val message: String) : VacancyCreateAction
}