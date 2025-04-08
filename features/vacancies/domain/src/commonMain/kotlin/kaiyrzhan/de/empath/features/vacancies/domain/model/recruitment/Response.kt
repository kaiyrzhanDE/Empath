package kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment

import kotlinx.datetime.Instant

public class Response(
    public val authorFullName: String,
    public val authorEmail: String,
    public val vacancyId: String,
    public val cvId: String,
    public val cvTitle: String,
    public val dateOfCreated: Instant?,
    public val status: String,
)