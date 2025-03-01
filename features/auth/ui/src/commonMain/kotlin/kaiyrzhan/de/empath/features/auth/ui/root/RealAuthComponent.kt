package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.login.RealLoginComponent
import kotlinx.serialization.Serializable

public class RealAuthComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, AuthComponent {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Login,
        serializer = Config.serializer(),
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        childComponentContext: ComponentContext,
    ): AuthComponent.Child = when (config) {
        is Config.Login -> createLoginComponent(childComponentContext)
//        is Config.CreateAccount -> createAccountComponent(childComponentContext)
//        is Config.Privacy -> privacyComponent(childComponentContext)
//        is Config.CodeConfirmation -> codeConfirmationComponent(childComponentContext, config)
//        is Config.EmailVerification -> emailVerificationComponent(childComponentContext, config)
//        is Config.PasswordRecovery -> recoveryPasswordComponent(childComponentContext)
//        is Config.OptionalAccountInfo -> optionalAccountInfoComponent(childComponentContext)
    }

    private fun createLoginComponent(componentContext: ComponentContext) =
        AuthComponent.Child.Login(
            RealLoginComponent(
                componentContext = componentContext,
                onLoginClick = {
                    TODO()
                }
            ),
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config
    }
}
