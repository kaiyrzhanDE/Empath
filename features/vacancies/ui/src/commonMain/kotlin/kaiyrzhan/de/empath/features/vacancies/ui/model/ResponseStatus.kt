package kaiyrzhan.de.empath.features.vacancies.ui.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class ResponseStatus(val type: String) {
    UNKNOWN("unknown"),
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    override fun toString(): String {
        return when(this){
            UNKNOWN -> "Unknown"
            PENDING -> "Pending"
            ACCEPTED -> "Accepted"
            REJECTED -> "Rejected"
        }
    }
}