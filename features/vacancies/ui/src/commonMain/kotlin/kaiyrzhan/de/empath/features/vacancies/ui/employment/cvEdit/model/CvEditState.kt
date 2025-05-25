package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model

import kaiyrzhan.de.empath.features.vacancies.ui.job.model.CvUi


internal sealed class CvEditState() {
    object Initial : CvEditState()
    object Loading : CvEditState()
    class Error(val message: String) : CvEditState()
    data class Success(
        val cv: CvUi,
    ) : CvEditState()

    companion object {
        fun default(): CvEditState {
            return Initial
        }
    }
}