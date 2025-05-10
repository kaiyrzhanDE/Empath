package kaiyrzhan.de.empath.features.vacancies.data.model.job

import kaiyrzhan.de.empath.features.vacancies.data.model.SalaryDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.SkillDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.AuthorDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CvDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String?,
    @SerialName("is_visible") val isVisible: Boolean?,
    @SerialName("salary") val salary: SalaryDTO,
    @SerialName("employment_types") val employmentTypes: List<SkillDTO>?,
    @SerialName("work_schedules") val workSchedules: List<SkillDTO>?,
    @SerialName("work_exp") val workExperiences: List<WorkExperienceDTO>?,
    @SerialName("work_formats") val workFormats: List<SkillDTO>?,
    @SerialName("skills") val skills: List<SkillDTO>?,
    @SerialName("education") val education: String?,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("email") val email: String?,
    @SerialName("additional_skills") val additionalSkills: List<SkillDTO>?,
    @SerialName("address") val address: String?,
    @SerialName("about_me") val aboutMe: String?,
    @SerialName("cv_file") val cvUrl: String?,
)

internal fun CvDTO.toDomain(): Cv {
    return Cv(
        id = id,
        title = title.orEmpty(),
        isVisible = isVisible == true,
        salary = salary.toDomain(),
        employmentTypes = employmentTypes
            ?.map { employmentType -> employmentType.toDomain() }
            .orEmpty(),
        workSchedules = workSchedules
            ?.map { workSchedule -> workSchedule.toDomain() }
            .orEmpty(),
        workExperiences = workExperiences
            ?.map { workExperience -> workExperience.toDomain() }
            .orEmpty(),
        workFormats = workFormats
            ?.map { workFormat -> workFormat.toDomain() }
            .orEmpty(),
        skills = skills
            ?.map { skill -> skill.toDomain() }
            .orEmpty(),
        education = education.orEmpty(),
        author = author.toDomain(),
        email = email.orEmpty(),
        additionalSkills = additionalSkills
            ?.map { skill -> skill.toDomain() }
            .orEmpty(),
        address = address.orEmpty(),
        aboutMe = aboutMe.orEmpty(),
        cvUrl = cvUrl.orEmpty(),
    )
}

