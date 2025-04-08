package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacanciesComponent {

    val state: StateFlow<VacanciesState>

    val vacancies: Flow<PagingData<VacancyUi>>

    val action: Flow<VacanciesAction>

    fun onEvent(event: VacanciesEvent)

}