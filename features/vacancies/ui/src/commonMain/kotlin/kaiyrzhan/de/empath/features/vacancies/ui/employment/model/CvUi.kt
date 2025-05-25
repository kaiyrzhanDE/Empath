package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class CvUi(
    val id: String,
    val author: AuthorUi,
    val title: String,
    val salary: SalaryUi,
    val cvUrl: String,
    val aboutMe: String,
    val skills: List<String>,
    val additionalSkills: List<String>,
    val isSelected: Boolean = false,
) {
    fun hasSkills(): Boolean {
        return skills.isNotEmpty() || additionalSkills.isNotEmpty()
    }
}

internal fun Cv.toUi(): CvUi {
    return CvUi(
        id = id,
        author = author.toUi(),
        title = title,
        salary = salary.toUi(),
        cvUrl = cvUrl,
        aboutMe = aboutMe,
        skills = skills,
        additionalSkills = additionalSkills,
    )
}