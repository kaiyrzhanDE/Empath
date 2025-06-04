package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail

import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailAction
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface CvDetailComponent {

    val state: StateFlow<CvDetailState>

    val action: Flow<CvDetailAction>

    fun onEvent(event: CvDetailEvent)

}