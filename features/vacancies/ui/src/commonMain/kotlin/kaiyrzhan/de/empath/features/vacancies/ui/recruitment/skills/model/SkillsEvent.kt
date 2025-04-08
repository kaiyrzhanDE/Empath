package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi

internal sealed interface SkillsEvent {
    data class SkillSelect(val skill: SkillUi) : SkillsEvent
    data class SkillRemove(val skill: SkillUi) : SkillsEvent
    data object SkillCreate : SkillsEvent
    data class Search(val query: String) : SkillsEvent
    data object SkillsSelectClick : SkillsEvent
    data object DismissClick : SkillsEvent
}