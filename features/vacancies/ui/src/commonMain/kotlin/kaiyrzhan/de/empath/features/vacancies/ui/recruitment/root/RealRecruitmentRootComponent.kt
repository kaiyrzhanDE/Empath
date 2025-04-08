package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
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
                    //TODO("Not yet implemented")
                },
                onVacancyEditClick = { id ->
                    //TODO("Not yet implemented")
                },
                onVacancyDetailClick = { id ->
                    //TODO("Not yet implemented")
                },
                onCvClick = { id ->
                    //TODO("Not yet implemented")
                },
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Vacancies : Config

    }

}