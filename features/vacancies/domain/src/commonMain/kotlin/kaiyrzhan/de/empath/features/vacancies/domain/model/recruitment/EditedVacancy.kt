package kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill

public class EditedVacancy(
    public val title: String,
    public val salary: Salary,
    public val address: String,
    public val author: Author,
    public val workExperience: String,
    public val workSchedules: List<String>,
    public val employmentTypes: List<String>,
    public val workFormats: List<String>,
    public val skills: List<Skill>,
    public val additionalDescription: String,
    public val additionalSkills: List<Skill>,
    public val email: String?,
)