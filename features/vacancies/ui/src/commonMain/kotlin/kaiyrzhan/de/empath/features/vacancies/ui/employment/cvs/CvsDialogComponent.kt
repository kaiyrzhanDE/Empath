package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs

import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsState
import kotlinx.coroutines.flow.StateFlow

internal interface CvsDialogComponent {

    val state: StateFlow<CvsState>

    fun onEvent(event: CvsEvent)

    companion object {
        const val DEFAULT_KEY: String = "cvs"
    }

}