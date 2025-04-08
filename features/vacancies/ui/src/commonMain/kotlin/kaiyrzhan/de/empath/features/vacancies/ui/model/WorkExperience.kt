package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class WorkExperience(val value: String, val res: StringResource) {
    UNKNOWN("unknown", Res.string.unknown),
    NO_EXPERIENCE("no_experience", Res.string.work_experience_no),
    ONE_TO_THREE_YEARS("1-3 year", Res.string.work_experience_1_3),
    THREE_TO_FIVE_YEARS("3-5 year", Res.string.work_experience_3_5),
    OVER_FIVE_YEARS("over 5 year", Res.string.work_experience_over_5);


    override fun toString(): String {
        return value
    }

    companion object {
        fun getWorkExperiences(): List<WorkExperienceUi> {
            return entries
                .filter { workExperience -> workExperience != UNKNOWN }
                .map { workExperience -> WorkExperienceUi(type = workExperience)
            }
        }
    }
}


internal data class WorkExperienceUi(
    val isSelected: Boolean = false,
    val type: WorkExperience,
)

internal fun List<WorkExperienceUi>.getSelected(): String {
    return first { it.isSelected }.type.value
}

internal fun List<WorkExperienceUi>.isChanged(): Boolean {
    return any { workExperience -> workExperience.isSelected }
}