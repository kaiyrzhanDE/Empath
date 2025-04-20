package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyEditState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model.VacancyFilterState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacancyEditComponent: BackHandlerOwner {

    val state: StateFlow<VacancyEditState>

    val workFormatsState: StateFlow<VacancyFilterState>

    val workSchedulesState: StateFlow<VacancyFilterState>

    val employmentTypesState: StateFlow<VacancyFilterState>

    val action: Flow<VacancyEditAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val skillsDialog: Value<ChildSlot<*, SkillsDialogComponent>>

    fun onEvent(event: VacancyEditEvent)

}