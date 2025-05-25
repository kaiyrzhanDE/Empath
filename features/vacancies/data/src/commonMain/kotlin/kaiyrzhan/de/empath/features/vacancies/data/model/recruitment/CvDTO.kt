package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.core.utils.logger.ifNull
import kaiyrzhan.de.empath.features.vacancies.data.model.SalaryDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Cv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CvDTO(
    @SerialName("id") val id: String,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("title") val title: String?,
    @SerialName("salary") val salary: SalaryDTO,
    @SerialName("cv_file") val cvUrl: String?,
    @SerialName("about_me") val aboutMe: String?,
    @SerialName("skills") val skills: List<SkillWeightDTO>?,
    @SerialName("additional_skills") val additionalSkills: List<SkillWeightDTO>?,
    @SerialName("weight") val weight: Double?,
)

internal fun CvDTO.toDomain(): Cv {
    return Cv(
        id = id,
        weight = weight.ifNull { 0.0 },
        author = author.toDomain(),
        title = title.orEmpty(),
        salary = salary.toDomain(),
        cvUrl = cvUrl.orEmpty(),
        aboutMe = aboutMe.orEmpty(),
        skills = skills
            ?.map { skill -> skill.toDomain() }
            .orEmpty(),
        additionalSkills = additionalSkills
            ?.map { skill -> skill.toDomain() }
            .orEmpty(),
    )
}