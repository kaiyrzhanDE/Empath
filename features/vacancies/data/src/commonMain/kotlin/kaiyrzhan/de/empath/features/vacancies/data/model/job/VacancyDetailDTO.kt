package kaiyrzhan.de.empath.features.vacancies.data.model.job

import kaiyrzhan.de.empath.core.utils.DatePattern
import kaiyrzhan.de.empath.core.utils.toInstantOrNull
import kaiyrzhan.de.empath.features.vacancies.data.model.SalaryDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.SkillDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class VacancyDetailDTO(
    @SerialName("title") val title: String?,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("salary") val salary: SalaryDTO,
    @SerialName("employment_types") val employmentTypes: List<SkillDTO>,
    @SerialName("work_schedules") val workSchedules: List<SkillDTO>,
    @SerialName("work_exp") val workExperience: String?,
    @SerialName("work_formats") val workFormats: List<SkillDTO>,
    @SerialName("skills") val skills: List<SkillDTO>,
    @SerialName("responsibility") val responsibilities: String?,
    @SerialName("requirements") val requirements: String?,
    @SerialName("education") val education: String?,
    @SerialName("additional_description") val additionalDescription: String?,
    @SerialName("additional_skills") val additionalSkills: List<SkillDTO>?,
    @SerialName("email") val email: String?,
    @SerialName("address") val address: String?,
    @SerialName("created_at") val dateOfCreated: String?,
    @SerialName("is_visible") val isVisible: Boolean,
)

internal fun VacancyDetailDTO.toDomain(): VacancyDetail {
    return VacancyDetail(
        title = title.orEmpty(),
        author = author.toDomain(),
        salary = salary.toDomain(),
        employmentTypes = employmentTypes.map { skill -> skill.toDomain() },
        workSchedules = workSchedules.map { skill -> skill.toDomain() },
        workExperience = workExperience.orEmpty(),
        workFormats = workFormats.map { skill -> skill.toDomain() },
        skills = skills.map { skill -> skill.toDomain() },
        responsibilities = responsibilities.orEmpty(),
        requirements = requirements.orEmpty(),
        education = education.orEmpty(),
        additionalDescription = additionalDescription.orEmpty(),
        additionalSkills = additionalSkills
            ?.map { skill -> skill.toDomain() }
            .orEmpty(),
        email = email.orEmpty(),
        address = address.orEmpty(),
        dateOfCreated = dateOfCreated.toInstantOrNull(DatePattern.DATE),
    )
}