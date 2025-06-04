package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.CvDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.VacancyDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.VacancyFiltersComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.VacancyCreateComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.VacanciesComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.VacancyEditComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.VacancyRecommendationsComponent

public interface RecruitmentRootComponent: BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Vacancies(val component: VacanciesComponent) : Child()
        internal class VacancyDetail(val component: VacancyDetailComponent) : Child()
        internal class VacancyFilters(val component: VacancyFiltersComponent) : Child()
        internal class VacancyCreate(val component: VacancyCreateComponent) : Child()
        internal class VacancyEdit(val component: VacancyEditComponent) : Child()
        internal class VacancyRecommendations(val component: VacancyRecommendationsComponent) : Child()
        internal class CvDetail(val component: CvDetailComponent) : Child()
    }
}