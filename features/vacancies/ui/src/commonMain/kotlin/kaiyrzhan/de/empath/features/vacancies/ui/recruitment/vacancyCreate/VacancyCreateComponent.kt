package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface VacancyCreateComponent: BackHandlerOwner {

    val state: StateFlow<VacancyCreateState>

    val action: Flow<VacancyCreateAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val skillsDialog: Value<ChildSlot<*, SkillsDialogComponent>>

    fun onEvent(event: VacancyCreateEvent)

}