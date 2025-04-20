package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.EditedVacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class VacancyRequest(
    @SerialName("title") val title: String,
    @SerialName("is_visible") val isVisible: Boolean,
    @SerialName("salary") val salary: SalaryRequest,
    @SerialName("employment_type_ids") val employmentTypeIds: List<String>,
    @SerialName("work_schedule_ids") val workScheduleIds: List<String>,
    @SerialName("work_exp") val workExperienceType: String,
    @SerialName("work_formats_id") val workFormatIds: List<String>,
    @SerialName("skills") val skills: List<SkillRequest>,
    @SerialName("responsibility") val responsibilities: String,
    @SerialName("requirements") val requirements: String,
    @SerialName("education") val educationType: String,
    @SerialName("additional_description") val additionalDescription: String?,
    @SerialName("additional_skills") val additionalSkills: List<SkillRequest>?,
    @SerialName("email") val email: String?,
    @SerialName("address") val address: String?,
)

internal fun NewVacancy.toData(): VacancyRequest {
    return VacancyRequest(
        title = title,
        isVisible = isVisible,
        salary = salary.toData(),
        employmentTypeIds = employmentTypesIds,
        workScheduleIds = workSchedulesIds,
        workExperienceType = this@toData.workExperienceType,
        workFormatIds = workFormatsIds,
        skills = skills.map { skill -> skill.toData() },
        responsibilities = responsibilities,
        requirements = requirements,
        educationType = educationType,
        additionalDescription = additionalDescription,
        additionalSkills = additionalSkills.map { skill -> skill.toData() },
        email = email,
        address = address,
    )
}

internal fun EditedVacancy.toData(): VacancyRequest {
    return VacancyRequest(
        title = title,
        isVisible = isVisible,
        salary = salary.toData(),
        employmentTypeIds = employmentTypesIds,
        workScheduleIds = workSchedulesIds,
        workFormatIds = workFormatsIds,
        skills = skills.map { skill -> skill.toData() },
        responsibilities = responsibilities,
        requirements = requirements,
        workExperienceType = workExperienceType,
        educationType = educationType,
        additionalDescription = additionalDescription,
        additionalSkills = additionalSkills.map { skill -> skill.toData() },
        email = email,
        address = address,
    )
}