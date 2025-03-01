package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.root.RealAuthComponent

import kotlinx.serialization.Serializable

public class RealRootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        childFactory = ::child,
    )

    override fun onBackClicked(): Unit = navigation.pop()

    private fun child(config: Config, childComponentContext: ComponentContext) =
        when (config) {
            Config.Auth -> createAuthComponent(childComponentContext)
        }

    private fun createAuthComponent(componentContext: ComponentContext) =
        RootComponent.Child.Auth(
            RealAuthComponent(
                componentContext = componentContext,
            ),
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config
    }
}
