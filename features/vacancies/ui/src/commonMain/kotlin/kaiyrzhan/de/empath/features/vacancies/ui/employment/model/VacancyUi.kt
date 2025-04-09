package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kotlinx.datetime.LocalDateTime

internal data class VacancyUi(
    val id: String,
    val title: String,
    val salary: Salary,
    val address: String,
    val author: Author,
    val workExperience: String,
    val workSchedules: List<String>,
    val employmentTypes: List<String>,
    val workFormats: List<String>,
    val skills: List<String>,
    val additionalSkills: List<String>,
    val email: String,
    val dateOfCreated: LocalDateTime?,
    val status: ResponseStatus,
){
    fun hasSkills(): Boolean {
        return skills.isNotEmpty() || additionalSkills.isNotEmpty()
    }
}


internal fun Vacancy.toUi(): VacancyUi {
    return VacancyUi(
        id = id,
        title = title,
        salary = salary,
        address = address,
        author = author,
        workExperience = workExperience,
        workSchedules = workSchedules,
        employmentTypes = employmentTypes,
        workFormats = workFormats,
        skills = skills,
        additionalSkills = additionalSkills,
        email = email,
        dateOfCreated = dateOfCreated.toLocalDateTime(),
        status = status.toEnumSafe(ResponseStatus.UNKNOWN),
    )
}
