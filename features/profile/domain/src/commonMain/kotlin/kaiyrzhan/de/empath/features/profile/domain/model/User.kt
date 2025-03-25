package kaiyrzhan.de.empath.features.profile.domain.model

import kotlinx.datetime.Instant

public data class User(
    val id: String,
    val nickname: String,
    val email: String,
    val password: String,
    val lastname: String,
    val name: String,
    val patronymic: String,
    val dateOfBirth: Instant?,
    val gender: Gender,
    val image: String,
){
    public enum class Gender(public val value: String){
        UNKNOWN("Unknown"),
        MALE("Male"),
        FEMALE("Female");

        override fun toString(): String {
            return value
        }
    }
}