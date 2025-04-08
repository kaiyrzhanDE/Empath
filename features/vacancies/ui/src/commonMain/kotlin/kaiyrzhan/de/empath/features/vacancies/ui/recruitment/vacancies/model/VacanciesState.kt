package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacanciesFiltersUi

internal data class VacanciesState(
    val currentTab: Tab = Tab.Vacancies,
    val tabs: List<Tab> = Tab.entries,
    val query: String = "",
    val vacanciesFilters: VacanciesFiltersUi = VacanciesFiltersUi(),
    val rejectedResponses: Set<ResponseUi> = emptySet(),
    val acceptedResponses: Set<ResponseUi> = emptySet(),
) {

    companion object {
        fun default(): VacanciesState {
            return VacanciesState(

            )
        }
    }
}