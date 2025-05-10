package kaiyrzhan.de.empath.features.vacancies.domain.model.employment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.WorkExperience

public class NewCv(
    public val title: String,
    public val isVisible: Boolean,
    public val salary: Salary,
    public val employmentTypeIds: List<String>,
    public val workScheduleIds: List<String>,
    public val workExperiences: List<WorkExperience>,
    public val workFormatIds: List<String>,
    public val skills: List<Skill>,
    public val education: String,
    public val email: String,
    public val additionalSkills: List<Skill>,
    public val address: String,
    public val aboutMe: String,
    public val cvUrl: String,
)