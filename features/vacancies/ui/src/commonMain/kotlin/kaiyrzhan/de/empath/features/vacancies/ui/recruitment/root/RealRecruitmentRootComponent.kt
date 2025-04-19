package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.RealVacancyDetailComponent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.RealVacancyFiltersComponent
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.RealVacancyCreateComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.RealVacanciesComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
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
            is Config.VacancyFilters -> createVacancyFiltersComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createVacanciesComponent(
        componentContext: ComponentContext,
    ): RecruitmentRootComponent.Child.Vacancies {
        return RecruitmentRootComponent.Child.Vacancies(
            component = RealVacanciesComponent(
                componentContext = componentContext,
                onVacanciesFiltersClick = { vacancyFilters ->
                    navigation.push(Config.VacancyFilters(vacancyFilters))
                },
                onVacancyCreateClick = { author ->
                    navigation.push(Config.VacancyCreate(author))
                },
                onVacancyEditClick = { id ->
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
                author = config.author,
                onBackClick = ::onBackClick,
                onVacancyCreateClick = ::onBackClick,
            )
        )
    }

    private fun createVacancyFiltersComponent(
        componentContext: ComponentContext,
        config: Config.VacancyFilters,
    ): RecruitmentRootComponent.Child.VacancyFilters {
        return RecruitmentRootComponent.Child.VacancyFilters(
            component = RealVacancyFiltersComponent(
                componentContext = componentContext,
                vacancyFilters = config.filters,
                onBackClick = { isFiltersUpdated, filters ->
                    if (isFiltersUpdated) {
                        reloadVacancies(filters)
                    } else {
                        onBackClick()
                    }
                },
            )
        )
    }

    private fun reloadVacancies(filters: VacancyFiltersUi) {
        navigation.pop {
            (stack.active.instance as? RecruitmentRootComponent.Child.Vacancies)
                ?.component
                ?.onEvent(VacanciesEvent.ApplyFilters(filters))
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Vacancies : Config

        @Serializable
        data class VacancyDetail(val vacancyId: String) : Config

        @Serializable
        data class VacancyFilters(
            val filters: VacancyFiltersUi,
        ) : Config

        @Serializable
        data class VacancyCreate(val author: AuthorUi) : Config
    }

}