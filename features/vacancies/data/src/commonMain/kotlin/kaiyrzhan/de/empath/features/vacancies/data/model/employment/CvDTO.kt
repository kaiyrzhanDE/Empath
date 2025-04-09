package kaiyrzhan.de.empath.features.vacancies.data.model.employment

import kaiyrzhan.de.empath.features.vacancies.data.model.SalaryDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.AuthorDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.toDomain
import kaiyrzhan.de.empath.features.vacancies.data.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CvDTO(
    @SerialName("id") val id: String,
    @SerialName("author") val author: AuthorDTO,
    @SerialName("title") val title: String?,
    @SerialName("salary") val salary: SalaryDTO,
    @SerialName("cv_file") val cvFileUrl: String?,
    @SerialName("about_me") val aboutMe: String?,
    @SerialName("skills") val skills: List<String>?,
    @SerialName("additional_skills") val additionalSkills: List<String>?,
)

internal fun CvDTO.toDomain(): Cv {
    return Cv(
        id = id,
        author = author.toDomain(),
        title = title.orEmpty(),
        salary = salary.toDomain(),
        cvFileUrl = cvFileUrl,
        aboutMe = aboutMe.orEmpty(),
        skills = skills.orEmpty(),
        additionalSkills = additionalSkills.orEmpty(),
    )
}