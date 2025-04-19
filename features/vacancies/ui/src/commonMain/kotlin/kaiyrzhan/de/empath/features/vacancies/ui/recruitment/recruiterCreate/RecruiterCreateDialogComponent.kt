package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.model.RecruiterCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.model.RecruiterCreateState
import kotlinx.coroutines.flow.StateFlow

internal interface RecruiterCreateDialogComponent {

    val state: StateFlow<RecruiterCreateState>

    fun onEvent(event: RecruiterCreateEvent)

    companion object{
        const val DEFAULT_KEY = "recruiterCreate"
    }
}