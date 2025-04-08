package kaiyrzhan.de.empath.features.vacancies.domain.model.job

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kotlinx.datetime.Instant

public class VacancyDetail(
    public val title: String,
    public val author: Author,
    public val salary: Salary,
    public val employmentTypes: List<Skill>,
    public val workSchedules: List<Skill>,
    public val workExperience: String,
    public val workFormats: List<Skill>,
    public val skills: List<Skill>,
    public val responsibilities: String,
    public val requirements: String,
    public val education: String,
    public val additionalDescription: String,
    public val additionalSkills: List<Skill>,
    public val email: String,
    public val address: String,
    public val dateOfCreated: Instant?,
)