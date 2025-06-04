package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetCvUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailAction
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailState
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get

internal class RealCvDetailComponent(
    componentContext: ComponentContext,
    private val cvId: String,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), CvDetailComponent {

    private val getCvUseCase: GetCvUseCase = get()

    override val state = MutableStateFlow<CvDetailState>(
        CvDetailState.default()
    )

    private val _action = Channel<CvDetailAction>(capacity = Channel.BUFFERED)
    override val action: Flow<CvDetailAction> = _action.receiveAsFlow()

    override fun onEvent(event: CvDetailEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is CvDetailEvent.LoadCv -> loadCv()
            is CvDetailEvent.BackClick -> onBackClick()
        }
    }

    init {
        loadCv()
    }

    private fun loadCv() {
        coroutineScope.launch {
            state.update { CvDetailState.Loading }
            getCvUseCase(cvId).onSuccess { cv ->
                state.update {
                    CvDetailState.Success(
                        cv = cv.toUi(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            CvDetailState.Error(error.toString())
                        }
                    }
                }
            }
        }
    }
}