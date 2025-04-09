package kaiyrzhan.de.empath.features.vacancies.domain.model.employment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author

public class Cv(
    public val id: String,
    public val author: Author,
    public val title: String,
    public val salary: Salary,
    public val cvFileUrl: String?,
    public val aboutMe: String,
    public val skills: List<String>,
    public val additionalSkills: List<String>,
)