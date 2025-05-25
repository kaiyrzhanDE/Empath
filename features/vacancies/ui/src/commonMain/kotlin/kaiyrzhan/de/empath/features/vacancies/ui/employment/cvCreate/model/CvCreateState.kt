package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model

import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.NewCvUi

internal sealed class CvCreateState() {
    object Initial : CvCreateState()
    object Loading : CvCreateState()
    class Error(val message: String) : CvCreateState()
    data class Success(
        val newCv: NewCvUi,
    ) : CvCreateState()

    companion object {
        fun default(): CvCreateState {
            return Success(
                newCv = NewCvUi.default(),
            )
        }
    }
}