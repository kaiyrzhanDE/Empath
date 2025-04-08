package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.NewVacancyUi

internal sealed class VacancyCreateState {
    object Initial : VacancyCreateState()
    object Loading : VacancyCreateState()
    class Error(val message: String) : VacancyCreateState()
    data class Success(
        val newVacancy: NewVacancyUi,
    ) : VacancyCreateState()

    companion object {
        fun default(): VacancyCreateState {
            return Success(
                newVacancy = NewVacancyUi.default(),
            )
        }
    }
}