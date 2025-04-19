package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.model

internal sealed interface RecruiterCreateEvent {
    data class CompanyNameChange(val companyName: String) : RecruiterCreateEvent
    data class CompanyDescriptionChange(val companyDescription: String) : RecruiterCreateEvent
    data class EmailChange(val email: String) : RecruiterCreateEvent
    data object ClickCreate : RecruiterCreateEvent
    data object DismissClick : RecruiterCreateEvent
}