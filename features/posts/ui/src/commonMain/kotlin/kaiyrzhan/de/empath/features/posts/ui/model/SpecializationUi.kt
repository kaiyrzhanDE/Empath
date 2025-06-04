package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.features.posts.domain.model.Specialization
import kotlinx.serialization.Serializable

@Serializable
internal data class SpecializationUi(
    val id: String,
    val name: String,
)

internal fun List<SpecializationUi>.getIds(): List<String> {
    return map { specialization -> specialization.id }
}

internal fun Specialization.toUi(): SpecializationUi {
    return SpecializationUi(
        id = id,
        name = name,
    )
}

internal fun SpecializationUi.toDomain(): Specialization {
    return Specialization(
        id = id,
        name = name,
    )
}