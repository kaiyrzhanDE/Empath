package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model

import kaiyrzhan.de.empath.features.vacancies.ui.job.model.VacancyDetailUi

internal sealed class VacancyDetailState {
    object Initial : VacancyDetailState()
    object Loading : VacancyDetailState()
    class Error(val message: String) : VacancyDetailState()
    data class Success(
        val vacancyDetail: VacancyDetailUi,
    ) : VacancyDetailState()

    companion object {
        fun default(): VacancyDetailState {
            return Initial
        }
    }
}