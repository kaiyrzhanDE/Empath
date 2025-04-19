package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseKeyUi

internal data class VacanciesState(
    val currentTab: Tab = Tab.Vacancies,
    val tabs: List<Tab> = Tab.entries,
    val vacancyFilters: VacancyFiltersUi = VacancyFiltersUi(),
    val acceptedResponsesKeys: Set<ResponseKeyUi> = emptySet(),
    val rejectedResponsesKeys: Set<ResponseKeyUi> = emptySet(),
) {

    companion object {
        fun default(): VacanciesState {
            return VacanciesState()
        }
    }
}