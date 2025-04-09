package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model

import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi

internal sealed interface CvsEvent {
    data class CvSelect(val cv: CvUi) : CvsEvent
    data object ReloadCvs : CvsEvent
    data object CvSelectClick : CvsEvent
    data object DismissClick : CvsEvent
}