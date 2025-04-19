package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class WorkExperience(val value: String, val res: StringResource) {
    UNKNOWN(value = "unknown", res = Res.string.unknown),
    NO_EXPERIENCE(value = "no_experience", res = Res.string.work_experience_no),
    ONE_TO_THREE_YEARS(value = "1-3 year", res = Res.string.work_experience_1_3),
    THREE_TO_FIVE_YEARS(value = "3-5 year", res = Res.string.work_experience_3_5),
    OVER_FIVE_YEARS(value = "over 5 year", res = Res.string.work_experience_over_5);


    override fun toString(): String {
        return value
    }

    companion object {
        fun getWorkExperiences(): List<WorkExperienceUi> {
            return entries
                .filter { workExperience -> workExperience != UNKNOWN }
                .map { workExperience ->
                    WorkExperienceUi(type = workExperience)
                }
        }
    }
}


internal data class WorkExperienceUi(
    val isSelected: Boolean = false,
    val type: WorkExperience,
)

internal fun List<WorkExperienceUi>.getSelected(): String {
    return first { it.isSelected }.type.toString()
}

internal fun List<WorkExperienceUi>.getSelectedTypes(): List<String> {
    return filter { workExperience -> workExperience.isSelected }
        .map { workExperience -> workExperience.type.toString() }
}

internal fun List<WorkExperienceUi>.isChanged(): Boolean {
    return any { workExperience -> workExperience.isSelected }
}