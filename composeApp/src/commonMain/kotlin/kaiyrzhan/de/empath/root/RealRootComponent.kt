package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.auth.ui.root.RealAuthComponent

import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class RealRootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RootComponent, KoinComponent {

    private val logger: BaseLogger by inject()

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        childFactory = ::createChild,
    )

    override fun onBackClicked(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        childComponentContext: ComponentContext,
    ): RootComponent.Child {
        logger.d(this.className(), "create: $config")
        return when (config) {
            is Config.Auth -> createAuthComponent(childComponentContext)
        }
    }

    private fun createAuthComponent(componentContext: ComponentContext): RootComponent.Child.Auth =
        RootComponent.Child.Auth(
            RealAuthComponent(
                componentContext = componentContext,
                onLoginClick = { logger.d("RootComponent", "onLoginClick") },
            ),
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config
    }
}
