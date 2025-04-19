package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model

internal sealed interface VacancyFiltersAction {
    class ShowSnackbar(val message: String): VacancyFiltersAction
}