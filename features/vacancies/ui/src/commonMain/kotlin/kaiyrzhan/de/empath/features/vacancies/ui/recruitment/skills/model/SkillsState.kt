package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kotlinx.serialization.Serializable

@Serializable
internal data class SkillsState(
    val query: String = "",
    val isKeySkills: Boolean = true,
    val originalSkills: List<SkillUi>,
    val editableSkills: List<SkillUi> = originalSkills,
) {
    fun isQueryValidLength(): Boolean {
        return query.length in 0..50
    }

    fun hasSkill(): Boolean {
        return editableSkills.any { skill -> skill.name == query }
    }
}