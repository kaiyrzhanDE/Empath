package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Cv
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi

internal data class CvUi(
    val id: String,
    val author: AuthorUi,
    val title: String,
    val salary: SalaryUi,
    val cvUrl: String,
    val aboutMe: String,
    val skills: List<SkillWeightUi>,
    val additionalSkills: List<SkillWeightUi>,
    val weight: Double,
){
    fun hasSkills(): Boolean {
        return skills.isNotEmpty() || additionalSkills.isNotEmpty()
    }
}

internal fun Cv.toUi(): CvUi {
    return CvUi(
        id = id,
        title = title,
        author = author.toUi(),
        salary = salary.toUi(),
        cvUrl = cvUrl,
        skills = skills.map { skill -> skill.toUi() },
        additionalSkills = additionalSkills.map { skill -> skill.toUi() },
        weight = weight,
        aboutMe = aboutMe,
    )
}