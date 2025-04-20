package kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill

public class EditedVacancy(
    public val title: String,
    public val salary: Salary,
    public val address: String,
    public val workExperienceType: String,
    public val educationType: String,
    public val workSchedulesIds: List<String>,
    public val employmentTypesIds: List<String>,
    public val workFormatsIds: List<String>,
    public val responsibilities: String,
    public val requirements: String,
    public val skills: List<Skill>,
    public val additionalDescription: String,
    public val additionalSkills: List<Skill>,
    public val email: String?,
    public val isVisible: Boolean,
)