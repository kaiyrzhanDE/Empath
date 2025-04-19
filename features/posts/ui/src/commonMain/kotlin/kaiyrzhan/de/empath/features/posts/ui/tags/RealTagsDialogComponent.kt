package kaiyrzhan.de.empath.features.posts.ui.tags

import androidx.paging.PagingData
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetTagsUseCase
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsEvent
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsState
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
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
internal class RealTagsDialogComponent(
    componentContext: ComponentContext,
    val tagsDialogState: TagsState,
    private val onDismissClick: (List<TagUi>) -> Unit,
) : BaseComponent(componentContext), TagsDialogComponent {
    private val getTagsUseCase: GetTagsUseCase by inject()

    override val state = MutableStateFlow(tagsDialogState)

    @OptIn(FlowPreview::class)
    private val queryFlow = state
        .map { it.query }
        .distinctUntilChanged()
        .debounce(1000)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tags: Flow<PagingData<TagUi>> = queryFlow.flatMapLatest { query ->
        getTagsUseCase(query).map { pagingData ->
            pagingData.map { tag ->
                tag.toUi()
            }
        }
    }

    override fun onEvent(event: TagsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is TagsEvent.DismissClick -> dismiss()
            is TagsEvent.TagSelect -> selectTag(event.tag)
            is TagsEvent.TagRemove -> removeTag(event.tag)
            is TagsEvent.TagCreate -> createTag()
            is TagsEvent.Search -> search(event.query)
            is TagsEvent.TagsSelectClick -> selectTags()
        }
    }

    private fun dismiss() {
        val currentState = state.value
        onDismissClick(currentState.originalTags)
    }

    private fun selectTags() {
        val currentState = state.value
        onDismissClick(currentState.editableTags)
    }

    private fun search(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query
            )
        }
    }

    private fun selectTag(selectedTag: TagUi) {
        state.update { currentState ->
            currentState.copy(
                editableTags = currentState.editableTags + selectedTag,
            )
        }
    }

    private fun removeTag(selectedTag: TagUi) {
        state.update { currentState ->
            currentState.copy(
                editableTags = currentState.editableTags - selectedTag,
            )
        }
    }

    private fun createTag() {
        state.update { currentState ->
            currentState.copy(
                editableTags = currentState.editableTags + TagUi.create(currentState.query),
            )
        }
    }
}