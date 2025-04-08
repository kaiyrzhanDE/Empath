package kaiyrzhan.de.empath.features.vacancies.ui.employment.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.VacanciesComponent

public interface EmploymentRootComponent: BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Vacancies(val component: VacanciesComponent) : Child()
//        internal class VacancyDetail(val component: VacanciesComponent) : Child()
//        internal class VacancyCreate(val component: VacanciesComponent) : Child()
//        internal class VacancyEdit(val component: VacanciesComponent) : Child()
//        internal class CreateCV(val component: VacanciesComponent) : Child()
    }
}