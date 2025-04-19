package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.paging.PagingData
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.RecruiterCreateDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacanciesComponent {

    val state: StateFlow<VacanciesState>

    val vacancies: Flow<PagingData<VacancyUi>>

    val responses: Flow<PagingData<ResponseUi>>

    val action: Flow<VacanciesAction>

    val recruiterDialog: Value<ChildSlot<*, RecruiterCreateDialogComponent>>

    fun onEvent(event: VacanciesEvent)

}