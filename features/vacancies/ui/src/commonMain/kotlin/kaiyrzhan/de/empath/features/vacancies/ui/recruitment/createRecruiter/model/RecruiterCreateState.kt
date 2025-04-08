package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RecruiterCreateState(
    val companyName: String,
    val companyDescription: String,
    val email: String,
    val isLoading: Boolean,
    val errorMessage: String,
) {
    companion object {
        fun default(): RecruiterCreateState {
            return RecruiterCreateState(
                companyName = "",
                companyDescription = "",
                email = "",
                isLoading = false,
                errorMessage = "",
            )
        }
    }
}