package kaiyrzhan.de.empath.features.vacancies.ui.employment.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.CvCreateComponent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.CvEditComponent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.VacanciesComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.CvDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.VacancyDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.VacancyFiltersComponent

public interface EmploymentRootComponent: BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Vacancies(val component: VacanciesComponent) : Child()
        internal class VacancyFilters(val component: VacancyFiltersComponent) : Child()
        internal class VacancyDetail(val component: VacancyDetailComponent) : Child()
        internal class CvCreate(val component: CvCreateComponent) : Child()
        internal class CvEdit(val component: CvEditComponent) : Child()
        internal class CvDetail(val component: CvDetailComponent) : Child()
    }
}