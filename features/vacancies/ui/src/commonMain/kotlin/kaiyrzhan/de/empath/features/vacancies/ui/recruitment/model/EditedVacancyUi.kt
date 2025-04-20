package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.EditedVacancy
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.Education
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperience
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelected
import kaiyrzhan.de.empath.features.vacancies.ui.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi

internal data class EditedVacancyUi(
    val title: String,
    val author: AuthorUi,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val address: String,
    val responsibilities: String,
    val requirements: String,
    val email: String,
    val additionalDescription: String,
    val selectedEmploymentTypes: List<SkillUi>,
    val selectedWorkSchedules: List<SkillUi>,
    val selectedWorkFormats: List<SkillUi>,
    val educations: List<EducationUi>,
    val workExperiences: List<WorkExperienceUi>,
    val skills: List<SkillUi>,
    val additionalSkills: List<SkillUi>,
    val isVisible: Boolean = true,
)

internal fun VacancyDetail.toUi(): EditedVacancyUi {
    val selectedEducation = education.toEnumSafe(Education.UNKNOWN) { enum, type ->
        enum.value.equals(type, ignoreCase = true)
    }
    val selectedWorkExperience = workExperience.toEnumSafe(WorkExperience.UNKNOWN) { enum, type ->
        enum.value.equals(type, ignoreCase = true)
    }
    return EditedVacancyUi(
        title = title,
        author = author.toUi(),
        salaryFrom = salary.from,
        salaryTo = salary.to,
        selectedEmploymentTypes = employmentTypes.map { employmentType -> employmentType.toUi() },
        selectedWorkSchedules = workSchedules.map { workSchedule -> workSchedule.toUi() },
        selectedWorkFormats = workFormats.map { workFormat -> workFormat.toUi() },
        workExperiences = WorkExperience.getWorkExperiences(selectedWorkExperience),
        educations = Education.getEducations(selectedEducation),
        responsibilities = responsibilities,
        requirements = requirements,
        skills = skills.map { skill -> skill.toUi() },
        additionalSkills = additionalSkills.map { skill -> skill.toUi() },
        additionalDescription = additionalDescription,
        email = email,
        address = address,
    )
}

internal fun EditedVacancyUi.toDomain(): EditedVacancy {
    return EditedVacancy(
        title = title,
        salary = Salary(
            from = salaryFrom,
            to = salaryTo,
        ),
        workSchedulesIds = selectedWorkSchedules.mapNotNull { it.id },
        employmentTypesIds = selectedEmploymentTypes.mapNotNull { it.id },
        workFormatsIds = selectedWorkFormats.mapNotNull { it.id },
        educationType = educations.getSelected(),
        workExperienceType = workExperiences.getSelected(),
        skills = skills.map { skill -> skill.toDomain() },
        additionalSkills = additionalSkills.map { skill -> skill.toDomain() },
        address = address,
        email = email,
        responsibilities = responsibilities,
        requirements = requirements,
        additionalDescription = additionalDescription,
        isVisible = isVisible,
    )
}