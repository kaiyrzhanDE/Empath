package kaiyrzhan.de.empath.features.vacancies.ui.employment.model

import kaiyrzhan.de.empath.core.utils.toInstantSafe
import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.WorkExperience
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class WorkExperienceUi(
    val id: Uuid = Uuid.random(),
    val companyName: String,
    val title: String,
    val description: String,
    val startDate: LocalDateTime?,
    val isRelevant: Boolean,
    val endDate: LocalDateTime?,
) {
    companion object {
        fun create(): WorkExperienceUi {
            return WorkExperienceUi(
                companyName = "",
                title = "",
                description = "",
                startDate = null,
                isRelevant = true,
                endDate = null,
            )
        }
    }
}

internal fun WorkExperienceUi.toDomain(): WorkExperience {
    return WorkExperience(
        companyName = companyName,
        title = title,
        description = description,
        startDate = startDate.toInstantSafe(),
        isRelevant = isRelevant,
        endDate = endDate.toInstantSafe(),
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun WorkExperience.toUi(): WorkExperienceUi {
    return WorkExperienceUi(
        companyName = companyName,
        title = title,
        description = description,
        startDate = startDate.toLocalDateTime(),
        endDate = endDate.toLocalDateTime(),
        isRelevant = isRelevant,
    )
}