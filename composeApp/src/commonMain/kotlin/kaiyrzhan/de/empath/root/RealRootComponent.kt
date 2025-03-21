package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.auth.ui.root.RealAuthComponent
import kotlinx.serialization.Serializable

public class RealRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), RootComponent {

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
        componentContext: ComponentContext,
    ): RootComponent.Child {
        logger.d(this.className(), "Root child: $config")
        return when (config) {
            is Config.Auth -> createAuthComponent(componentContext)
        }
    }

    private fun createAuthComponent(componentContext: ComponentContext): RootComponent.Child.Auth =
        RootComponent.Child.Auth(
            RealAuthComponent(
                componentContext = componentContext,
                onLoginClick = {  },
            ),
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config
    }
}
