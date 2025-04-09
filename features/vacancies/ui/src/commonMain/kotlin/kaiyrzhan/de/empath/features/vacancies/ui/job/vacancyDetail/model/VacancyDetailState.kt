package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model

import kaiyrzhan.de.empath.features.vacancies.ui.job.model.VacancyDetailUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus

internal sealed class VacancyDetailState {
    object Initial : VacancyDetailState()
    object Loading : VacancyDetailState()
    class Error(val message: String) : VacancyDetailState()
    data class Success(
        val vacancyDetail: VacancyDetailUi,
        val isDeleting: Boolean = false,
        val isEditing: Boolean = false,
        val isResponding: Boolean = false,
        val responseStatus: ResponseStatus = ResponseStatus.UNKNOWN,
    ) : VacancyDetailState()

    companion object {
        fun default(): VacancyDetailState {
            return Initial
        }
    }
}