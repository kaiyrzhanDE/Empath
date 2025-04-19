package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters

import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model.VacancyFiltersState
import kotlinx.coroutines.flow.StateFlow

internal interface VacancyFiltersComponent: BackHandlerOwner {

    val state: StateFlow<VacancyFiltersState>

    fun onEvent(event: VacancyFiltersEvent)

}