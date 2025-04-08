package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class Education(val value: String, val res: StringResource) {
    UNKNOWN("unknown", Res.string.unknown),
    SCHOOL("school", Res.string.education_school),
    BACHELOR("bachelor", Res.string.education_bachelor),
    MASTER("master", Res.string.education_master),
    DOCTORATE("doctorate", Res.string.education_doctorate);

    companion object {
        fun getEducations(): List<EducationUi> {
            return entries
                .filter { it != UNKNOWN }
                .map { education -> EducationUi(type = education) }
        }

    }
}

internal data class EducationUi(
    val isSelected: Boolean = false,
    val type: Education,
)

internal fun List<EducationUi>.getSelected(): String {
    return first { it.isSelected }.type.value
}

internal fun List<EducationUi>.isChanged(): Boolean {
    return any { education -> education.isSelected }
}