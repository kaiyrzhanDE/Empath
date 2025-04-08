package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail

import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailAction
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacancyDetailComponent {

    val state: StateFlow<VacancyDetailState>

    val action: Flow<VacancyDetailAction>

    fun onEvent(event: VacancyDetailEvent)

}