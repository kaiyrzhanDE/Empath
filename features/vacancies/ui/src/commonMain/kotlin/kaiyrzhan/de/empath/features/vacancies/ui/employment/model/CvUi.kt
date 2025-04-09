package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
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
    val cvFileUrl: String?,
    val aboutMe: String,
    val skills: List<String>,
    val additionalSkills: List<String>,
    val isSelected: Boolean = false,
) {
    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun sample(
            isSelected: Boolean = false,
        ): CvUi {
            return CvUi(
                id = Uuid.random().toString(),
                author = AuthorUi(
                    name = "Sansyzbaev Dias Ermekuly"
                ),
                title = "Senior Android Developer",
                salary = SalaryUi(
                    to = Random.nextInt(100000, 150000),
                    from = Random.nextInt(100000, 150000),
                ),
                cvFileUrl = null,
                aboutMe = "Passionate Android developer with solid experience in building modern, scalable mobile applications using Kotlin, Jetpack Compose, and clean architecture principles. Always eager to learn new technologies and deliver high-quality user experiences.",
                skills = listOf(
                    "Kotlin",
                    "Java",
                    "Android",
                    "Java",
                    "Android",
                    "Java",
                    "Android",
                    "Java",
                    "Android"
                ),
                additionalSkills = listOf(
                    "Swift",
                    "iOS",
                    "Java",
                    "Android",
                    "Java",
                    "Android",
                    "Java",
                    "Android"
                ),
                isSelected = isSelected,
            )
        }
    }

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
        cvFileUrl = cvFileUrl,
        aboutMe = aboutMe,
        skills = skills,
        additionalSkills = additionalSkills,
    )
}