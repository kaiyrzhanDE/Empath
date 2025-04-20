package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.EditedVacancyUi

internal sealed class VacancyEditState {
    object Initial : VacancyEditState()
    object Loading : VacancyEditState()
    class Error(val message: String) : VacancyEditState()
    data class Success(
        val originalVacancy: EditedVacancyUi,
        val editableVacancy: EditedVacancyUi = originalVacancy,
    ) : VacancyEditState()
    companion object {
        fun default(): VacancyEditState {
            return Initial
        }
    }
}