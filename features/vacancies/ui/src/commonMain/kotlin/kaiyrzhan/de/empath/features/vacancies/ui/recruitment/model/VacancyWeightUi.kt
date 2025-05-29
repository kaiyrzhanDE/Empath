package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyWeight

internal data class VacancyWeightUi(
    val id: String,
    val name: String,
    val weight: Double,
)

internal fun VacancyWeight.toUi(): VacancyWeightUi {
    return VacancyWeightUi(
        id = id,
        name = name,
        weight = weight,
    )
}