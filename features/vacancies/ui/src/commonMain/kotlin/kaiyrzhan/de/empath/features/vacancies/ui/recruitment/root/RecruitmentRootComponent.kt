package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.VacanciesComponent

public interface RecruitmentRootComponent: BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Vacancies(val component: VacanciesComponent) : Child()
//        internal class RecruitmentVacancyDetail(val component: RecruitmentVacanciesComponent) : Child()
//        internal class RecruitmentVacancyCreate(val component: RecruitmentVacanciesComponent) : Child()
//        internal class RecruitmentVacancyEdit(val component: RecruitmentVacanciesComponent) : Child()
//        internal class CreateCV(val component: RecruitmentVacanciesComponent) : Child()
    }
}