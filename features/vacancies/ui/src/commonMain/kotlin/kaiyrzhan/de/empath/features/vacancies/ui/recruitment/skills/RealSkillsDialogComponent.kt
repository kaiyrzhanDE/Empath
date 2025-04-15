package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills

import androidx.paging.PagingData
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetSkillsUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
internal class RealSkillsDialogComponent(
    componentContext: ComponentContext,
    skillsDialogState: SkillsState,
    private val onDismissClick: (selectedSkills: List<SkillUi>, isKeySkills: Boolean) -> Unit,
) : BaseComponent(componentContext), SkillsDialogComponent {
    private val getSkillsUseCase: GetSkillsUseCase by inject()

    override val state = MutableStateFlow(skillsDialogState)

    @OptIn(FlowPreview::class)
    private val queryFlow = state
        .map { it.query }
        .distinctUntilChanged()
        .debounce(1000)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val skills: Flow<PagingData<SkillUi>> = queryFlow.flatMapLatest { query ->
        getSkillsUseCase(query).map { pagingData ->
            pagingData.map { skill ->
                skill.toUi()
            }
        }
    }

    override fun onEvent(event: SkillsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is SkillsEvent.DismissClick -> dismiss()
            is SkillsEvent.SkillSelect -> selectSkill(event.skill)
            is SkillsEvent.SkillRemove -> removeSkill(event.skill)
            is SkillsEvent.SkillCreate -> createSkill()
            is SkillsEvent.Search -> search(event.query)
            is SkillsEvent.SkillsSelectClick -> selectSkills()
        }
    }

    private fun dismiss() {
        val currentState = state.value
        val updatedSkills = currentState.originalSkills.map { skill -> skill.copy(isSelected = true) }
        onDismissClick(updatedSkills, currentState.isKeySkills)
    }

    private fun selectSkills() {
        val currentState = state.value
        val updatedSkills = currentState.editableSkills.map { skill -> skill.copy(isSelected = true) }
        onDismissClick(updatedSkills, currentState.isKeySkills)
    }

    private fun search(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query
            )
        }
    }

    private fun selectSkill(selectedSkill: SkillUi) {
        state.update { currentState ->
            currentState.copy(
                editableSkills = currentState.editableSkills + selectedSkill,
            )
        }
    }

    private fun removeSkill(selectedSkill: SkillUi) {
        state.update { currentState ->
            currentState.copy(
                editableSkills = currentState.editableSkills - selectedSkill,
            )
        }
    }

    private fun createSkill() {
        state.update { currentState ->
            currentState.copy(
                editableSkills = currentState.editableSkills + SkillUi.create(currentState.query),
            )
        }
    }
}