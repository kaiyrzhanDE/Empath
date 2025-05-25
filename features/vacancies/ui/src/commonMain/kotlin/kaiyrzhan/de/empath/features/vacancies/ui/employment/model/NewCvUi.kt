package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.NewCv
import kaiyrzhan.de.empath.features.vacancies.ui.model.Education
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelected
import kaiyrzhan.de.empath.features.vacancies.ui.model.toDomain

internal data class NewCvUi(
    val title: String,
    val isVisible: Boolean,
    val salary: SalaryUi,
    val selectedEmploymentTypes: List<SkillUi>,
    val selectedWorkSchedules: List<SkillUi>,
    val workExperiences: List<WorkExperienceUi>,
    val selectedWorkFormats: List<SkillUi>,
    val skills: List<SkillUi>,
    val educations: List<EducationUi>,
    val email: String,
    val additionalSkills: List<SkillUi>,
    val address: String,
    val aboutMe: String,
    val cvFile: FileUi?,
) {
    fun isChanged(): Boolean {
        return title.isNotEmpty() ||
                isVisible != true ||
                salary.from != null ||
                salary.to != null ||
                selectedEmploymentTypes.isNotEmpty() ||
                selectedWorkSchedules.isNotEmpty() ||
                workExperiences.isNotEmpty() ||
                selectedWorkFormats.isNotEmpty() ||
                skills.isNotEmpty() ||
                educations.any { education -> education.isSelected } ||
                email.isNotEmpty() ||
                additionalSkills.isNotEmpty() ||
                address.isNotEmpty() ||
                aboutMe.isNotEmpty() ||
                cvFile != null
    }

    fun isFilled(): Boolean {
        return title.isNotEmpty() &&
                salary.from != null &&
                selectedEmploymentTypes.isNotEmpty() &&
                selectedWorkSchedules.isNotEmpty() &&
                workExperiences.isNotEmpty() &&
                selectedWorkFormats.isNotEmpty() &&
                skills.isNotEmpty() &&
                educations.any { education -> education.isSelected } &&
                email.isNotEmpty()
    }

    companion object {
        fun default(): NewCvUi {
            return NewCvUi(
                title = "",
                isVisible = true,
                salary = SalaryUi(
                    from = null,
                    to = null,
                ),
                selectedEmploymentTypes = emptyList(),
                selectedWorkSchedules = emptyList(),
                workExperiences = emptyList(),
                selectedWorkFormats = emptyList(),
                skills = emptyList(),
                educations = Education.getEducations(),
                email = "",
                additionalSkills = emptyList(),
                address = "",
                aboutMe = "",
                cvFile = null,
            )
        }
    }
}

internal fun NewCvUi.toDomain(): NewCv {
    return NewCv(
        title = title,
        isVisible = isVisible,
        salary = salary.toDomain(),
        employmentTypeIds = selectedEmploymentTypes.mapNotNull { it.id },
        workScheduleIds = selectedWorkSchedules.mapNotNull { it -> it.id },
        workExperiences = workExperiences.map { workExperience -> workExperience.toDomain() },
        workFormatIds = selectedWorkFormats.mapNotNull { it.id },
        skills = skills.map { skill -> skill.toDomain() },
        education = educations.getSelected(),
        email = email,
        additionalSkills = additionalSkills.map { skill -> skill.toDomain() },
        address = address,
        aboutMe = aboutMe,
        cvUrl = cvFile?.url.orEmpty(),
    )
}