package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kotlinx.serialization.SerialName

public class CreateRecruiterRequest(
    @SerialName("company_name") public val companyName: String,
    @SerialName("about_us") public val companyDescription: String,
    @SerialName("email") public val email: String,
)