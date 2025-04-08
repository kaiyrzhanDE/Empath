package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface SkillsDialogComponent {

    val state: StateFlow<SkillsState>

    val skills: Flow<PagingData<SkillUi>>

    fun onEvent(event: SkillsEvent)

    companion object {
        const val DEFAULT_KEY: String = "skills"
    }
}