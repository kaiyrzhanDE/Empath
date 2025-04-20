package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi

internal sealed class VacancyFilterState {
    object Initial : VacancyFilterState()
    object Loading : VacancyFilterState()
    class Error(val message: String) : VacancyFilterState()
    data class Success(
        val filters: List<SkillUi>,
    ) : VacancyFilterState()

    companion object {
        fun default(): VacancyFilterState {
            return Initial
        }
    }
}