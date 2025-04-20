package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class Education(val value: String, val res: StringResource) {
    UNKNOWN(value = "unknown", res = Res.string.unknown),
    SCHOOL(value = "school", res = Res.string.education_school),
    BACHELOR(value = "bachelor", res = Res.string.education_bachelor),
    MASTER(value = "master", res = Res.string.education_master),
    DOCTORATE(value = "doctorate", res = Res.string.education_doctorate);

    override fun toString(): String {
        return value
    }

    companion object {
        fun getEducations(): List<EducationUi> {
            return entries
                .filter { it != UNKNOWN }
                .map { education -> EducationUi(type = education) }
        }

        fun getEducations(selectedEducation: Education): List<EducationUi> {
            return entries
                .filter { it != UNKNOWN }
                .map { education ->
                    EducationUi(
                        type = education,
                        isSelected = education == selectedEducation,
                    )
                }
        }

    }
}

internal data class EducationUi(
    val isSelected: Boolean = false,
    val type: Education,
)

internal fun List<EducationUi>.getSelected(): String {
    return first { it.isSelected }.type.toString()
}

internal fun List<EducationUi>.getSelectedTypes(): List<String> {
    return filter { education -> education.isSelected }
        .map { education -> education.type.toString() }
}

internal fun List<EducationUi>.isChanged(): Boolean {
    return any { education -> education.isSelected }
}