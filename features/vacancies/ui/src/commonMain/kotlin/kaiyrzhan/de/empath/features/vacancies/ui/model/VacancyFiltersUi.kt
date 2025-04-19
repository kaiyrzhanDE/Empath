package kaiyrzhan.de.empath.features.vacancies.ui.model

import kotlinx.serialization.Serializable

@Serializable
internal data class VacancyFiltersUi(
    val query: String = "",
    val salaryFrom: Int? = null,
    val salaryTo: Int? = null,
    val selectedWorkExperienceTypes: List<String> = emptyList(),
    val selectedWorkFormatTypes: List<String> = emptyList(),
    val selectedEducationTypes: List<String> = emptyList(),
    val excludeWords: String = "",
    val includeWords: String = "",
)