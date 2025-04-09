package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model

import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kotlinx.serialization.Serializable

@Serializable
internal sealed class CvsState {

    @Serializable
    object Initial : CvsState()

    @Serializable
    object Loading : CvsState()

    @Serializable
    class Error(val message: String) : CvsState()

    @Serializable
    data class Success(
        val cvs: List<CvUi>,
    ) : CvsState()

    companion object {
        fun default(): CvsState {
            return Initial
        }
    }
}