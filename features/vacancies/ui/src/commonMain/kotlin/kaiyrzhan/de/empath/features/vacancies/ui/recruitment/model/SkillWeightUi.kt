package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.SkillWeight

internal data class SkillWeightUi(
    val name: String,
    val weight: Double,
)

internal fun SkillWeight.toUi(): SkillWeightUi {
    return SkillWeightUi(
        name = name,
        weight = weight,
    )
}