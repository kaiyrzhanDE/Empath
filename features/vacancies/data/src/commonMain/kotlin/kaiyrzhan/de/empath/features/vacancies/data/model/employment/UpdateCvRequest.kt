package kaiyrzhan.de.empath.features.vacancies.data.model.employment

import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.SalaryRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.SkillRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.toData
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class UpdateCvRequest(
    @SerialName("title") val title: String,
    @SerialName("is_visible") val isVisible: Boolean,
    @SerialName("salary") val salary: SalaryRequest,
    @SerialName("employment_type_ids") val employmentTypeIds: List<String>,
    @SerialName("work_schedule_ids") val workScheduleIds: List<String>,
    @SerialName("work_exp") val workExperiences: List<WorkExperienceRequest>,
    @SerialName("work_formats_id") val workFormatIds: List<String>,
    @SerialName("skills") val skills: List<SkillRequest>,
    @SerialName("education") val education: String,
    @SerialName("email") val email: String,
    @SerialName("additional_skills") val additionalSkills: List<SkillRequest>?,
    @SerialName("address") val address: String?,
    @SerialName("about_me") val aboutMe: String?,
    @SerialName("cv_file") val cvUrl: String?,
)

internal fun Cv.toData(): UpdateCvRequest {
    return UpdateCvRequest(
        title = title,
        isVisible = isVisible,
        salary = salary.toData(),
        employmentTypeIds = employmentTypes
            .filter { employmentType -> employmentType.id != null }
            .map { employmentType -> employmentType.id.orEmpty() },
        workScheduleIds = workSchedules
            .filter { workSchedule -> workSchedule.id != null }
            .map { workSchedule -> workSchedule.id.orEmpty() },
        workExperiences = workExperiences.map { experience -> experience.toData() },
        workFormatIds = workFormats
            .filter { workFormat -> workFormat.id != null }
            .map { workFormat -> workFormat.id.orEmpty() },
        skills = skills.map { skill -> skill.toData() },
        education = education,
        email = email,
        additionalSkills = additionalSkills.map { skill -> skill.toData() },
        address = address.ifBlank { null },
        aboutMe = aboutMe.ifBlank { null },
        cvUrl = cvUrl.ifBlank { null },
    )
}