package kaiyrzhan.de.empath.features.vacancies.domain.model.job

import kotlinx.datetime.Instant

public class WorkExperience(
    public val companyName: String,
    public val title: String,
    public val description: String,
    public val startDate: Instant?,
    public val isRelevant: Boolean,
    public val endDate: Instant?,
)