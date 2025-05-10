package kaiyrzhan.de.empath.features.vacancies.domain.model.job

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author

public class Cv(
    public val id: String,
    public val title: String,
    public val isVisible: Boolean,
    public val salary: Salary,
    public val employmentTypes: List<Skill>,
    public val workSchedules: List<Skill>,
    public val workExperiences: List<WorkExperience>,
    public val workFormats: List<Skill>,
    public val skills: List<Skill>,
    public val education: String,
    public val author: Author,
    public val email: String,
    public val additionalSkills: List<Skill>,
    public val address: String,
    public val aboutMe: String,
    public val cvUrl: String,
)
