package kaiyrzhan.de.empath.features.vacancies.ui.model

import kaiyrzhan.de.empath.core.utils.currentTimeZone
import kaiyrzhan.de.empath.core.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

internal data class ResponseUi(
    val authorFullName: String,
    val vacancyId: String,
    val authorEmail: String,
    val cvId: String,
    val cvTitle: String,
    val dateOfCreated: LocalDateTime?,
    val status: ResponseStatus,
){
    companion object{
        fun sample(): ResponseUi{
            return ResponseUi(
                authorFullName = "Sansyzbaev Dias Ermekuly",
                vacancyId = "",
                authorEmail = "kaiyrzhan.de@gmail.com",
                cvId =  "",
                cvTitle = "cvTitle",
                dateOfCreated = Clock.System.now().toLocalDateTime(currentTimeZone),
                status = ResponseStatus.ACCEPTED,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ResponseUi) return false
        return vacancyId == other.vacancyId && cvId == other.cvId
    }

    override fun hashCode(): Int {
        var result = vacancyId.hashCode()
        result = 31 * result + cvId.hashCode()
        return result
    }
}

internal fun Response.toUi(): ResponseUi {
    return ResponseUi(
        authorFullName = authorFullName,
        authorEmail = authorEmail,
        vacancyId = vacancyId,
        cvId = cvId,
        cvTitle = cvTitle,
        dateOfCreated = dateOfCreated.toLocalDateTime(),
        status = status.toEnumSafe(ResponseStatus.UNKNOWN),
    )
}

