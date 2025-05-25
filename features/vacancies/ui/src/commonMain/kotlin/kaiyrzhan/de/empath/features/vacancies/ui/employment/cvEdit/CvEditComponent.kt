package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model.CvCreateAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model.CvCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model.CvCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model.CvEditState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyFilterState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface CvEditComponent : BackHandlerOwner {

    val state: StateFlow<CvEditState>

    val workFormatsState: StateFlow<VacancyFilterState>

    val workSchedulesState: StateFlow<VacancyFilterState>

    val employmentTypesState: StateFlow<VacancyFilterState>

    val action: Flow<CvEditAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val skillsDialog: Value<ChildSlot<*, SkillsDialogComponent>>

    val datePicker: Value<ChildSlot<*, DatePickerComponent>>

    fun onEvent(event: CvEditEvent)

}