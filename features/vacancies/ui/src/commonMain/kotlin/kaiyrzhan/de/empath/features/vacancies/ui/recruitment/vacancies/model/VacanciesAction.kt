package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

internal sealed interface VacanciesAction {
    class ShowSnacbar(val message: String): VacanciesAction

}