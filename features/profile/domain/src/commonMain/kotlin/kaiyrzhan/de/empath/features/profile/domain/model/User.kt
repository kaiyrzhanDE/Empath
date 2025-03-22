package kaiyrzhan.de.empath.features.profile.domain.model

public data class User(
    val id: String,
    val nickname: String,
    val email: String,
    val password: String,
    val lastname: String,
    val name: String,
    val patronymic: String,
    val dateOfBirth: String,
    val gender: Gender,
    val image: String,
){
    public enum class Gender(public val value: String){
        UNKNOWN("unknown"),
        MALE("male"),
        FEMALE("female"),
    }
}