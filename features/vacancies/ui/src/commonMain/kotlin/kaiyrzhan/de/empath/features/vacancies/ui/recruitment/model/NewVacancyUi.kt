package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kaiyrzhan.de.empath.features.vacancies.ui.model.Education
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperience
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelected
import kaiyrzhan.de.empath.features.vacancies.ui.model.isChanged
import kaiyrzhan.de.empath.features.vacancies.ui.model.toDomain

internal data class NewVacancyUi(
    val title: String,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val address: String,
    val responsibilities: String,
    val requirements: String,
    val email: String,
    val additionalDescription: String,
    val isVisible: Boolean,
    val selectedEmploymentTypes: List<SkillUi>,
    val selectedWorkSchedules: List<SkillUi>,
    val selectedWorkFormats: List<SkillUi>,
    val educations: List<EducationUi>,
    val workExperiences: List<WorkExperienceUi>,
    val skills: List<SkillUi>,
    val additionalSkills: List<SkillUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() ||
                salaryFrom != null ||
                salaryTo != null ||
                address.isNotBlank() ||
                responsibilities.isNotBlank() ||
                requirements.isNotBlank() ||
                email.isNotBlank() ||
                selectedEmploymentTypes.isChanged() ||
                selectedWorkSchedules.isChanged() ||
                selectedWorkFormats.isChanged() ||
                educations.isChanged() ||
                workExperiences.isChanged() ||
                additionalDescription.isNotBlank() ||
                skills.isChanged() ||
                additionalSkills.isChanged()
    }

    fun isFilled(): Boolean {
        return title.isNotBlank() &&
                salaryFrom != null &&
                responsibilities.isNotBlank() &&
                requirements.isNotBlank() &&
                email.isNotBlank() &&
                selectedEmploymentTypes.isChanged() &&
                selectedWorkSchedules.isChanged() &&
                selectedWorkFormats.isChanged() &&
                educations.isChanged() &&
                workExperiences.isChanged() &&
                skills.isChanged()
    }

    companion object {
        fun default(): NewVacancyUi {
            return NewVacancyUi(
                title = "",
                salaryTo = null,
                salaryFrom = null,
                address = "",
                responsibilities = "",
                requirements = "",
                email = "",
                isVisible = true,
                selectedEmploymentTypes = emptyList(),
                selectedWorkSchedules = emptyList(),
                selectedWorkFormats = emptyList(),
                educations = Education.getEducations(),
                workExperiences = WorkExperience.getWorkExperiences(),
                skills = emptyList(),
                additionalDescription = "",
                additionalSkills = emptyList(),
            )
        }
    }
}

internal fun NewVacancyUi.toDomain(): NewVacancy {
    return NewVacancy(
        title = title,
        isVisible = isVisible,
        salary = Salary(
            from = salaryFrom,
            to = salaryTo,
        ),
        address = address,
        responsibilities = responsibilities,
        requirements = requirements,
        workExperienceType = workExperiences.getSelected(),
        workSchedulesIds = selectedWorkSchedules.mapNotNull { it.id },
        employmentTypesIds = selectedEmploymentTypes.mapNotNull { it.id },
        workFormatsIds = selectedWorkFormats.mapNotNull { it.id },
        skills = skills.map { skill -> skill.toDomain() },
        educationType = educations.getSelected(),
        additionalDescription = additionalDescription,
        additionalSkills = additionalSkills.map { it.toDomain() },
        email = email
    )
}

