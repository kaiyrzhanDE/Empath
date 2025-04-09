package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.responses
import empath.core.uikit.generated.resources.vacancies
import org.jetbrains.compose.resources.StringResource

internal enum class Tab(
    internal val res: StringResource,
) {
    Vacancies(Res.string.vacancies),
    Responses(Res.string.responses),
}