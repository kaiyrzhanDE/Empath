package kaiyrzhan.de.empath.features.vacancies.data.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kotlinx.serialization.SerialName

internal class VacancyDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("salary") val salary: SalaryDTO,
    @SerialName("address") val address: String?,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("work_exp") val workExperience: String?,
    @SerialName("work_schedules") val workSchedules: List<String>,
    @SerialName("employment_types") val employmentTypes: List<String>,
    @SerialName("work_formats") val workFormats: List<String>,
    @SerialName("skills") val skills: List<String>,
    @SerialName("additional_skills") val additionalSkills: List<String>?,
    @SerialName("email") val email: String?,
    @SerialName("created_at") val dateOfCreated: String?,
)

internal fun VacancyDTO.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        title = title.orEmpty(),
        salary = salary.toDomain(),
        address = address.orEmpty(),
        author = author.toDomain(),
        workExperience = workExperience.orEmpty(),
        workSchedules = workSchedules,
        employmentTypes = employmentTypes,
        workFormats = workFormats,
        skills = skills,
        additionalSkills = additionalSkills.orEmpty(),
        email = email.orEmpty(),
        dateOfCreated = dateOfCreated.orEmpty(),
    )
}