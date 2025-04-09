package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies

import androidx.paging.PagingData
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.CvsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacanciesComponent {

    val state: StateFlow<VacanciesState>

    val vacancies: Flow<PagingData<VacancyUi>>

    val responses: Flow<PagingData<VacancyUi>>

    val action: Flow<VacanciesAction>

    val cvsDialog: Value<ChildSlot<*, CvsDialogComponent>>

    fun onEvent(event: VacanciesEvent)

}