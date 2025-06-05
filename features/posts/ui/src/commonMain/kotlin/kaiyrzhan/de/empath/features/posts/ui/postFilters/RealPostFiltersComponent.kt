package kaiyrzhan.de.empath.features.posts.ui.postFilters

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetSpecializationsUseCase
import kaiyrzhan.de.empath.features.posts.ui.model.PostFiltersUi
import kaiyrzhan.de.empath.features.posts.ui.model.PostReactionType
import kaiyrzhan.de.empath.features.posts.ui.model.SpecializationUi
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersAction
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersEvent
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersState
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.SpecializationsState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.collections.map

internal class RealPostFiltersComponent(
    componentContext: ComponentContext,
    private val postFilters: PostFiltersUi,
    private val onBackClick: (isPostFiltersUpdated: Boolean, vacancyFilters: PostFiltersUi) -> Unit,
) : BaseComponent(componentContext), PostFiltersComponent {

    private val getSpecializationsUseCase: GetSpecializationsUseCase by inject()

    override val state = MutableStateFlow<PostFiltersState>(
        PostFiltersState.default(postFilters)
    )

    override val specializationsState = MutableStateFlow<SpecializationsState>(
        SpecializationsState.default()
    )

    @OptIn(FlowPreview::class)
    private val specializationQuery = state
        .map { state -> state.specializationQuery }
        .debounce(500)
        .distinctUntilChanged()

    private val _action = Channel<PostFiltersAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostFiltersAction> = _action.receiveAsFlow()

    init {
        observeQuery()
    }

    override fun onEvent(event: PostFiltersEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostFiltersEvent.ReactionTypeSelect -> selectReactionType(event.reactionType)
            is PostFiltersEvent.QueryChange -> changeQuery(event.query)
            is PostFiltersEvent.SpecializationQueryChange -> changeSpecializationQuery(event.query)
            is PostFiltersEvent.BackClick -> backClick()
            is PostFiltersEvent.Clear -> clear()
            is PostFiltersEvent.Apply -> apply()
            is PostFiltersEvent.SpecializationSelect -> selectSpecialization(event.specialization)
            is PostFiltersEvent.SpecializationRemove -> removeSpecialization(event.specialization)
            is PostFiltersEvent.IncludeWordsChange -> changeIncludeWords(event.includeWords)
            is PostFiltersEvent.ExcludeWordsChange -> changeExcludeWords(event.excludeWords)
        }
    }

    private fun backClick() {
        val currentState = state.value
        val postFilters = PostFiltersUi(
            query = currentState.query,
            excludeWords = currentState.excludeWords,
            includeWords = currentState.includeWords,
            postReactionType = currentState.reactionType,
            selectedSpecializations = currentState.selectedSpecializations,
        )
        onBackClick(false, postFilters)
    }

    private fun changeQuery(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query,
            )
        }
    }

    private fun changeExcludeWords(excludeWords: String) {
        state.update { currentState ->
            currentState.copy(
                excludeWords = excludeWords,
            )
        }
    }

    private fun changeIncludeWords(includeWords: String) {
        state.update { currentState ->
            currentState.copy(
                includeWords = includeWords,
            )
        }
    }


    private fun selectReactionType(reactionType: PostReactionType) {
        state.update { currentState ->
            currentState.copy(
                reactionType = reactionType,
            )
        }
    }

    private fun clear() {
        state.update {
            PostFiltersState.default(PostFiltersUi())
        }
    }

    private fun apply() {
        val currentState = state.value
        val postFilters = PostFiltersUi(
            query = currentState.query,
            excludeWords = currentState.excludeWords,
            includeWords = currentState.includeWords,
            postReactionType = currentState.reactionType,
            selectedSpecializations = currentState.selectedSpecializations,
        )
        onBackClick(true, postFilters)
    }

    private fun changeSpecializationQuery(query: String) {
        state.update { currentState ->
            currentState.copy(
                specializationQuery = query,
            )
        }
    }

    private fun observeQuery() {
        coroutineScope.launch {
            specializationQuery.collectLatest { query ->
                loadSpecializations(query)
            }
        }
    }

    private fun loadSpecializations(query: String) {
        specializationsState.update { SpecializationsState.Loading }
        coroutineScope.launch {
            getSpecializationsUseCase(
                query = query,
            ).onSuccess { specializations ->
                specializationsState.update {
                    SpecializationsState.Success(
                        specializations = specializations.data.map { specialization -> specialization.toUi() },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        specializationsState.update {
                            SpecializationsState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun selectSpecialization(specialization: SpecializationUi) {
        state.update { currentState ->
            currentState.copy(
                selectedSpecializations = currentState.selectedSpecializations.plus(specialization)
            )
        }
    }

    private fun removeSpecialization(specialization: SpecializationUi) {
        state.update { currentState ->
            currentState.copy(
                selectedSpecializations = currentState.selectedSpecializations.minus(specialization)
            )
        }
    }
}