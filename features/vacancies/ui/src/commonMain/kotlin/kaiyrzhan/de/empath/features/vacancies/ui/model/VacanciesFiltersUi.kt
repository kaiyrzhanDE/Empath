package kaiyrzhan.de.empath.features.vacancies.ui.model

internal data class VacanciesFiltersUi(
    val salaryFrom: Int? = null,
    val salaryTo: Int? = null,
    val workExperiences: List<String> = emptyList(),
    val workSchedules: List<String> = emptyList(),
    val workFormats: List<String> = emptyList(),
    val excludeWords: List<String> = emptyList(),
    val includeWords: List<String> = emptyList(),
)