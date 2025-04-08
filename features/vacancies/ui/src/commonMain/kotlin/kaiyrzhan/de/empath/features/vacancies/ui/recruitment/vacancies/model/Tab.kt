package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class Tab(
    internal val res: StringResource,
) {
    Vacancies(Res.string.vacancies),
    Responses(Res.string.responses),
}