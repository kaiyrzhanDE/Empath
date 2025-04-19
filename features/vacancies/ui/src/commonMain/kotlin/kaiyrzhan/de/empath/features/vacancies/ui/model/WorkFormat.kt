package kaiyrzhan.de.empath.features.vacancies.ui.model

import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.StringResource

internal enum class WorkFormat(val value: String, val res: StringResource) {
    UNKNOWN(value = "unknown", res = Res.string.unknown),
    REMOTE(value = "remote", res = Res.string.remote),
    ONSITE(value = "onsite", res = Res.string.onsite),
    HYBRID(value = "hybrid", res = Res.string.hybrid);

    override fun toString(): String {
        return value
    }

    companion object {
        fun getWorkFormats(): List<WorkFormatUi> {
            return WorkFormat.entries
                .filter { workFormat -> workFormat != UNKNOWN }
                .map { workFormat ->
                    WorkFormatUi(type = workFormat)
                }
        }
    }
}

internal fun List<WorkFormatUi>.getSelected(): String {
    return first { it.isSelected }.type.toString()
}

internal fun List<WorkFormatUi>.getSelectedTypes(): List<String> {
    return filter { workFormat -> workFormat.isSelected }
        .map { workFormat -> workFormat.type.toString() }
}


internal data class WorkFormatUi(
    val isSelected: Boolean = false,
    val type: WorkFormat,
)