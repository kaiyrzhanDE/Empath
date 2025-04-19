package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi

internal data class VacanciesState(
    val currentTab: Tab = Tab.Vacancies,
    val tabs: List<Tab> = Tab.entries,
    val vacanciesFilters: VacancyFiltersUi = VacancyFiltersUi(),
    val respondedVacancies: Set<VacancyUi> = emptySet(),
) {

    companion object {
        fun default(): VacanciesState {
            return VacanciesState(

            )
        }
    }
}