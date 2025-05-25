package kaiyrzhan.de.empath.features.vacancies.ui.job.model

import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.FileUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.Education
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelected
import kaiyrzhan.de.empath.features.vacancies.ui.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toDomain
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kotlin.uuid.ExperimentalUuidApi

internal data class CvUi(
    val id: String,
    val title: String,
    val isVisible: Boolean,
    val salary: SalaryUi,
    val selectedEmploymentTypes: List<SkillUi>,
    val selectedWorkSchedules: List<SkillUi>,
    val workExperiences: List<WorkExperienceUi>,
    val selectedWorkFormats: List<SkillUi>,
    val skills: List<SkillUi>,
    val educations: List<EducationUi>,
    val author: AuthorUi,
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
}


@OptIn(ExperimentalUuidApi::class)
internal fun Cv.toUi(): CvUi {
    val selectedEducation = education.toEnumSafe(Education.UNKNOWN) { enum, type ->
        enum.value.equals(type, ignoreCase = true)
    }
    return CvUi(
        id = id,
        title = title,
        isVisible = isVisible,
        salary = salary.toUi(),
        selectedEmploymentTypes = employmentTypes.map { employmentType -> employmentType.toUi() },
        selectedWorkSchedules = workSchedules.map { workSchedule -> workSchedule.toUi() },
        workExperiences = workExperiences.map { experience -> experience.toUi() },
        selectedWorkFormats = workFormats.map { workFormat -> workFormat.toUi() },
        address = address,
        aboutMe = aboutMe,
        cvFile = FileUi(url = cvUrl),
        email = email,
        additionalSkills = additionalSkills.map { skill -> skill.toUi() },
        skills = skills.map { skill -> skill.toUi() },
        educations = Education.getEducations(selectedEducation),
        author = author.toUi(),
    )
}

internal fun CvUi.toDomain(): Cv {
    return Cv(
        title = title,
        isVisible = isVisible,
        salary = salary.toDomain(),
        employmentTypes = selectedEmploymentTypes.map { employmentType -> employmentType.toDomain() },
        workSchedules = selectedWorkSchedules.map { workSchedule -> workSchedule.toDomain() },
        workExperiences = workExperiences.map { experience -> experience.toDomain() },
        workFormats = selectedWorkFormats.map { workFormat -> workFormat.toDomain() },
        address = address,
        aboutMe = aboutMe,
        cvUrl = cvFile?.url.orEmpty(),
        email = email,
        additionalSkills = additionalSkills.map { skill -> skill.toDomain() },
        skills = skills.map { skill -> skill.toDomain() },
        education = educations.getSelected(),
        id = id,
        author = author.toDomain(),
    )
}