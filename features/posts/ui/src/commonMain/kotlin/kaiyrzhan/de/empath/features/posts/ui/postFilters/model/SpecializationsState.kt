package kaiyrzhan.de.empath.features.posts.ui.postFilters.model

import kaiyrzhan.de.empath.features.posts.ui.model.SpecializationUi

internal sealed class SpecializationsState {
    data object Initial : SpecializationsState()
    data object Loading : SpecializationsState()
    data class Error(val message: String) : SpecializationsState()
    data class Success(
        val specializations: List<SpecializationUi>,
    ) : SpecializationsState()

    companion object {
        fun default(): SpecializationsState {
            return Initial
        }
    }
}