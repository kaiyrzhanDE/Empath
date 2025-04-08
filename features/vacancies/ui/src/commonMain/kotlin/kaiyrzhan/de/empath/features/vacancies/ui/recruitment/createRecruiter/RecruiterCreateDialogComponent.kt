package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model.RecruiterCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model.RecruiterCreateState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface RecruiterCreateDialogComponent {

    val state: StateFlow<RecruiterCreateState>

    fun onEvent(event: RecruiterCreateEvent)
}