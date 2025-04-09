package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author

internal data class CvUi(
    val id: String,
    val author: Author,
    val title: String,
    val salary: Salary,
    val cvFileUrl: String?,
    val aboutMe: String,
    val skills: List<String>,
    val additionalSkills: List<String>,
)

internal fun Cv.toUi(): CvUi {
    return CvUi(
        id = id,
        author = author,
        title = title,
        salary = salary,
        cvFileUrl = cvFileUrl,
        aboutMe = aboutMe,
        skills = skills,
        additionalSkills = additionalSkills,
    )
}