package kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary

public class Cv(
    public val id: String,
    public val author: Author,
    public val title: String,
    public val salary: Salary,
    public val cvUrl: String,
    public val aboutMe: String,
    public val skills: List<SkillWeight>,
    public val additionalSkills: List<SkillWeight>,
    public val weight: Double,
)
