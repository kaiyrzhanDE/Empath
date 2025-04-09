package kaiyrzhan.de.empath.features.vacancies.ui.employment.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.RealVacanciesComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.RealVacancyDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root.RealRecruitmentRootComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root.RecruitmentRootComponent
import kotlinx.serialization.Serializable

public class RealEmploymentRootComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), EmploymentRootComponent {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, EmploymentRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Vacancies,
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): EmploymentRootComponent.Child {
        logger.d(this.className(), "Employment child: $config")
        return when (config) {
            is Config.Vacancies -> createVacanciesComponent(componentContext)
            is Config.VacancyDetail -> createVacancyDetailComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacanciesComponent(
        componentContext: ComponentContext,
    ): EmploymentRootComponent.Child.Vacancies {
        return EmploymentRootComponent.Child.Vacancies(
            component = RealVacanciesComponent(
                componentContext = componentContext,
                onVacancyDetailClick = { vacancyId, status ->
                    navigation.push(
                        Config.VacancyDetail(
                            vacancyId = vacancyId,
                            responseStatus = status,
                        )
                    )
                },
                onVacanciesFiltersClick = {

                },
            )
        )
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacancyDetailComponent(
        componentContext: ComponentContext,
        config: Config.VacancyDetail,
    ): EmploymentRootComponent.Child.VacancyDetail {
        return EmploymentRootComponent.Child.VacancyDetail(
            component = RealVacancyDetailComponent(
                componentContext = componentContext,
                vacancyId = config.vacancyId,
                responseStatus = config.responseStatus,
                onBackClick = ::onBackClick,
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Vacancies : Config

        @Serializable
        data class VacancyDetail(
            val vacancyId: String,
            val responseStatus: ResponseStatus,
        ) : Config
    }

}