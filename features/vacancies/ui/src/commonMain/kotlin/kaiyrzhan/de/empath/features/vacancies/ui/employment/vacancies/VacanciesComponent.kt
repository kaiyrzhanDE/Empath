package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacanciesComponent {

    val state: StateFlow<VacanciesState>

    val vacancies: Flow<PagingData<VacancyUi>>

    val responses: Flow<PagingData<VacancyUi>>

    val action: Flow<VacanciesAction>

    fun onEvent(event: VacanciesEvent)

}