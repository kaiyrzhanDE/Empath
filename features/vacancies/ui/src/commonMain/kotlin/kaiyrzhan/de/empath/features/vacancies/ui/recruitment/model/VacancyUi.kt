package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.core.utils.currentTimeZone
import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperience
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

internal data class VacancyUi(
    val id: String,
    val title: String,
    val salary: SalaryUi,
    val address: String,
    val author: AuthorUi,
    val workExperience: WorkExperience,
    val workSchedules: List<String>,
    val employmentTypes: List<String>,
    val workFormats: List<String>,
    val skills: List<String>,
    val additionalSkills: List<String>,
    val email: String?,
    val dateOfCreated: LocalDateTime?,
) {
    fun hasSkills(): Boolean {
        return skills.isNotEmpty() || additionalSkills.isNotEmpty()
    }
}

internal fun Vacancy.toUi(): VacancyUi {
    return VacancyUi(
        id = id,
        title = title,
        salary = salary.toUi(),
        address = address,
        author = author.toUi(),
        workExperience = workExperience.toEnumSafe(WorkExperience.UNKNOWN) { enum, type ->
            enum.value.equals(type, ignoreCase = true)
        },
        workSchedules = workSchedules,
        employmentTypes = employmentTypes,
        workFormats = workFormats,
        skills = skills,
        additionalSkills = additionalSkills,
        email = email,
        dateOfCreated = dateOfCreated.toLocalDateTime(),
    )
}
