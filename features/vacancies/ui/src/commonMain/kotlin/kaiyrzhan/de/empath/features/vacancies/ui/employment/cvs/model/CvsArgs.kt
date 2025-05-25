package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model

import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kotlinx.serialization.Serializable

@Serializable
internal data class CvsArgs(
    val vacancy: VacancyUi? = null,
    val isIndicator: Boolean = vacancy == null,
)