package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.network.token.Token
import kaiyrzhan.de.empath.core.network.token.TokenProvider
import kaiyrzhan.de.empath.core.network.token.isAuthorized
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.auth.ui.root.RealAuthRootComponent
import kaiyrzhan.de.empath.main.RealMainRootComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.koin.core.component.get

public class RealRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), RootComponent {

    private val tokenProvider: TokenProvider = get()

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = getInitialConfig(),
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        logger.d(this.className(), "Root child: $config")
        return when (config) {
            is Config.Auth -> createAuthComponent(componentContext)
            is Config.Main -> createMainComponent(componentContext)
        }
    }

    private fun createAuthComponent(componentContext: ComponentContext): RootComponent.Child.Auth {
        return RootComponent.Child.Auth(
            RealAuthRootComponent(
                componentContext = componentContext,
                onLoginClick = { navigation.replaceAll(Config.Main) },
            ),
        )
    }

    private fun createMainComponent(componentContext: ComponentContext): RootComponent.Child.Main {
        return RootComponent.Child.Main(
            RealMainRootComponent(
                componentContext = componentContext,
                onLogOutClick = ::logOut,
            ),
        )
    }

    private fun getInitialConfig(): Config = runBlocking {
        val token: Token? = tokenProvider.getCurrentToken()
        return@runBlocking if (token.isAuthorized()) {
            Config.Main
        } else {
            Config.Auth
        }
    }

    private fun logOut() {
        coroutineScope.launch {
            val result = tokenProvider.deleteLocalToken()
            logger.d(this.className(), "LogOut result: $result")
            navigation.replaceAll(Config.Auth)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config

        @Serializable
        data object Main : Config
    }
}
