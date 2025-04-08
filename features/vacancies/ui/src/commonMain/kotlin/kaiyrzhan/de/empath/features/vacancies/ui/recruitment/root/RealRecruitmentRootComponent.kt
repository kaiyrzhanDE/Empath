package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

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
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.RealVacancyDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.RealVacancyCreateComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.RealVacanciesComponent
import kotlinx.serialization.Serializable

public class RealRecruitmentRootComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), RecruitmentRootComponent {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RecruitmentRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Vacancies,
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): RecruitmentRootComponent.Child {
        logger.d(this.className(), "Recruitment child: $config")
        return when (config) {
            is Config.Vacancies -> createVacanciesComponent(componentContext)
            is Config.VacancyDetail -> createVacancyDetailComponent(componentContext, config)
            is Config.VacancyCreate -> createVacancyCreateComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacanciesComponent(
        componentContext: ComponentContext,
    ): RecruitmentRootComponent.Child.Vacancies {
        return RecruitmentRootComponent.Child.Vacancies(
            component = RealVacanciesComponent(
                componentContext = componentContext,
                onVacanciesFiltersClick = { filters ->
                    //TODO("Not yet implemented")
                },
                onVacancyCreateClick = {
                    navigation.push(Config.VacancyCreate)
                },
                onVacancyEditClick = { id ->
                    //TODO("Not yet implemented")
                },
                onVacancyDetailClick = { vacancyId ->
                    navigation.push(Config.VacancyDetail(vacancyId))
                },
                onCvClick = { id ->
                    //TODO("Not yet implemented")
                },
            )
        )
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacancyDetailComponent(
        componentContext: ComponentContext,
        config: Config.VacancyDetail,
    ): RecruitmentRootComponent.Child.VacancyDetail {
        return RecruitmentRootComponent.Child.VacancyDetail(
            component = RealVacancyDetailComponent(
                componentContext = componentContext,
                vacancyId = config.vacancyId,
                onBackClick = ::onBackClick,
                onVacancyEditClick = {
                    //TODO("Not yet implemented")
                },
                onVacancyDeleteClick = {
                    //TODO("Not yet implemented")
                },
            )
        )
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacancyCreateComponent(
        componentContext: ComponentContext,
        config: Config.VacancyCreate,
    ): RecruitmentRootComponent.Child.VacancyCreate {
        return RecruitmentRootComponent.Child.VacancyCreate(
            component = RealVacancyCreateComponent(
                componentContext = componentContext,
                onBackClick = ::onBackClick,
                onVacancyCreateClick = {

                }
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Vacancies : Config

        @Serializable
        data class VacancyDetail(val vacancyId: String) : Config

        @Serializable
        data object VacancyCreate : Config
    }

}