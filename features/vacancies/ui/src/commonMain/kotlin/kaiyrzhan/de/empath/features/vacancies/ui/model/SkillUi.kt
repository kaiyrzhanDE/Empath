package kaiyrzhan.de.empath.features.vacancies.ui.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kotlinx.serialization.Serializable

@Serializable
internal data class SkillUi(
    val name: String,
    val id: String?,
    val isSelected: Boolean = false,
) {
    companion object {
        fun create(name: String): SkillUi {
            return SkillUi(
                name = name,
                id = null,
                isSelected = true,
            )
        }
    }
}

internal fun List<SkillUi>.isChanged(): Boolean {
    return any { skill -> skill.isSelected }
}

internal fun Skill.toUi(): SkillUi {
    return SkillUi(
        name = name,
        id = id,
    )
}

internal fun SkillUi.toDomain(): Skill {
    return Skill(
        id = id,
        name = name,
    )
}