package kaiyrzhan.de.empath.features.vacancies.domain.model.employment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author
import kotlinx.datetime.Instant

public class Vacancy(
    public val id: String,
    public val title: String,
    public val salary: Salary,
    public val address: String,
    public val author: Author,
    public val workExperience: String,
    public val workSchedules: List<String>,
    public val employmentTypes: List<String>,
    public val workFormats: List<String>,
    public val skills: List<String>,
    public val additionalSkills: List<String>,
    public val email: String?,
    public val dateOfCreated: Instant?,
    public val status: String,
)