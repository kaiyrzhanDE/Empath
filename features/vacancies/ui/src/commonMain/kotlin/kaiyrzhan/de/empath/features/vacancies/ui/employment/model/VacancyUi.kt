package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.core.utils.currentTimeZone
import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kaiyrzhan.de.empath.features.vacancies.ui.model.SalaryUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
internal data class VacancyUi(
    val id: String,
    val title: String,
    val salary: SalaryUi,
    val address: String,
    val author: AuthorUi,
    val workExperience: String,
    val workSchedules: List<String>,
    val employmentTypes: List<String>,
    val workFormats: List<String>,
    val skills: List<String>,
    val additionalSkills: List<String>,
    val email: String,
    val dateOfCreated: LocalDateTime?,
    val status: ResponseStatus,
){
    fun hasSkills(): Boolean {
        return skills.isNotEmpty() || additionalSkills.isNotEmpty()
    }

    companion object {
        fun sample(
            status: ResponseStatus
        ): VacancyUi {
            return VacancyUi(
                title = "Android Developer",
                salary = SalaryUi(
                    to = 100000,
                    from = null,
                ),
                address = "Almaty",
                author = AuthorUi(
                    name = "Kaspi.kz"
                ),
                workExperience = "1-3 year",
                workSchedules = listOf("5/2", "4/3"),
                employmentTypes = listOf("Full-time", "Part-time"),
                workFormats = listOf("Hybrid", "Remote", "Onsite"),
                email = "kaiyrzhan.de@gmail.com",
                skills = listOf(
                    "Retrofit", "Okhttp", "Dagger",
                    "Koin", "Okhttp", "Dagger",
                ),
                additionalSkills = listOf("Kotlin", "Java", "Android"),
                id = "1",
                dateOfCreated = Clock.System.now().toLocalDateTime(currentTimeZone),
                status = status,
            )
        }
    }
}


internal fun Vacancy.toUi(): VacancyUi {
    return VacancyUi(
        id = id,
        title = title,
        salary = salary.toUi(),
        address = address,
        author = author.toUi(),
        workExperience = workExperience,
        workSchedules = workSchedules,
        employmentTypes = employmentTypes,
        workFormats = workFormats,
        skills = skills,
        additionalSkills = additionalSkills,
        email = email,
        dateOfCreated = dateOfCreated.toLocalDateTime(),
        status = status.toEnumSafe(ResponseStatus.UNKNOWN),
    )
}
