package kaiyrzhan.de.empath.features.vacancies.ui.job.model

import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kotlinx.datetime.LocalDateTime

internal data class VacancyDetailUi(
    val title: String,
    val author: AuthorUi,
    val salary: SalaryUi,
    val employmentTypes: List<SkillUi>,
    val workSchedules: List<SkillUi>,
    val workExperience: String,
    val workFormats: List<SkillUi>,
    val skills: List<SkillUi>,
    val responsibilities: String,
    val requirements: String,
    val education: String,
    val additionalDescription: String,
    val additionalSkills: List<SkillUi>,
    val email: String,
    val address: String,
    val dateOfCreated: LocalDateTime?,
)

internal fun VacancyDetail.toUi(): VacancyDetailUi{
    return VacancyDetailUi(
        title = title,
        author = author.toUi(),
        salary = salary.toUi(),
        employmentTypes = employmentTypes.map { it.toUi() },
        workSchedules = workSchedules.map { it.toUi() },
        workExperience = workExperience,
        workFormats = workFormats.map { it.toUi() },
        skills = skills.map { it.toUi() },
        responsibilities = responsibilities,
        requirements = requirements,
        education = education,
        additionalDescription = additionalDescription,
        additionalSkills = additionalSkills.map { it.toUi() },
        email = email,
        address = address,
        dateOfCreated = dateOfCreated.toLocalDateTime(),
    )
}


