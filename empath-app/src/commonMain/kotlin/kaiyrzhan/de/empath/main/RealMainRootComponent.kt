package kaiyrzhan.de.empath.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.articles.ui.root.RealArticlesRootComponent
import kaiyrzhan.de.empath.features.profile.ui.root.RealProfileRootComponent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.root.RealEmploymentRootComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root.RealRecruitmentRootComponent
import kotlinx.serialization.Serializable

internal class RealMainRootComponent(
    componentContext: ComponentContext,
    private val onLogOutClick: () -> Unit,
) : BaseComponent(componentContext), MainRootComponent, BackHandlerOwner {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, MainRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Profile,
        childFactory = ::createChild,
    )

    override fun onBackClick() = navigation.pop()

    override fun onProfileTabClick() = navigation.bringToFront(Config.Profile)

    override fun onArticlesTabClick() = navigation.bringToFront(Config.Articles)

    override fun onMenuTabClick() = navigation.bringToFront(Config.Menu)

    override fun onEmploymentTabClick() = navigation.bringToFront(Config.Employment)

    override fun onRecruitmentTabClick() = navigation.bringToFront(Config.Recruitment)

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): MainRootComponent.Child {
        logger.d(this.className(), "Main child: $config")
        return when (config) {
            is Config.Profile -> createProfileComponent(componentContext)
            is Config.Articles -> createArticlesComponent(componentContext)
            is Config.Menu -> createMenuComponent(componentContext)
            is Config.Employment -> createEmploymentComponent(componentContext)
            is Config.Recruitment -> createRecruitmentComponent(componentContext)
        }
    }

    private fun createProfileComponent(componentContext: ComponentContext): MainRootComponent.Child.Profile {
        return MainRootComponent.Child.Profile(
            RealProfileRootComponent(
                componentContext = componentContext,
                onLogOutClick = onLogOutClick,
            )
        )
    }

    private fun createArticlesComponent(
        componentContext: ComponentContext,
    ): MainRootComponent.Child.Articles {
        return MainRootComponent.Child.Articles(
            RealArticlesRootComponent(
                componentContext = componentContext,
            )
        )
    }

    private fun createMenuComponent(
        componentContext: ComponentContext,
    ): MainRootComponent.Child.Menu {
        return MainRootComponent.Child.Menu()
    }

    private fun createEmploymentComponent(
        componentContext: ComponentContext,
    ): MainRootComponent.Child.Employment {
        return MainRootComponent.Child.Employment(
            component = RealEmploymentRootComponent(
                componentContext = componentContext,
            )
        )
    }

    private fun createRecruitmentComponent(
        componentContext: ComponentContext,
    ): MainRootComponent.Child.Recruitment {
        return MainRootComponent.Child.Recruitment(
            component = RealRecruitmentRootComponent(
                componentContext = componentContext,
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Profile : Config

        @Serializable
        data object Articles : Config

        @Serializable
        data object Menu : Config

        @Serializable
        data object Employment : Config

        @Serializable
        data object Recruitment : Config
    }
}