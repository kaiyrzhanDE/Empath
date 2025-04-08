package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.VacanciesFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi

internal data class VacanciesState(
    val currentTab: Tab = Tab.Vacancies,
    val tabs: List<Tab> = Tab.entries,
    val query: String = "",
    val vacanciesFilters: VacanciesFiltersUi = VacanciesFiltersUi(),
) {

    companion object {
        fun default(): VacanciesState {
            return VacanciesState(

            )
        }
    }
}