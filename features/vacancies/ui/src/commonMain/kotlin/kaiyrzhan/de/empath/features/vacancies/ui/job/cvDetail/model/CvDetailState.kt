package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model

import kaiyrzhan.de.empath.features.vacancies.ui.job.model.CvUi

internal sealed class CvDetailState {
    object Initial : CvDetailState()
    object Loading : CvDetailState()
    class Error(val message: String) : CvDetailState()
    data class Success(
        val cv: CvUi,
    ) : CvDetailState()

    companion object {
        fun default(): CvDetailState {
            return Initial
        }
    }
}