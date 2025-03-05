package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.RealCodeConfirmationComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.RealEmailVerificationComponent
import kaiyrzhan.de.empath.features.auth.ui.login.RealLoginComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

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
        is Config.EmailVerification -> createEmailVerificationComponent(childComponentContext)
        is Config.CodeConfirmation -> createCodeConfirmationComponent(childComponentContext, config)
//        is Config.CreateAccount -> createAccountComponent(childComponentContext)
//        is Config.Privacy -> privacyComponent(childComponentContext)
//        is Config.PasswordRecovery -> recoveryPasswordComponent(childComponentContext)
//        is Config.OptionalAccountInfo -> optionalAccountInfoComponent(childComponentContext)
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createLoginComponent(componentContext: ComponentContext) =
        AuthComponent.Child.Login(
            RealLoginComponent(
                componentContext = componentContext,
                onLoginClick = { navigation.push(Config.EmailVerification) },
            ),
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createEmailVerificationComponent(componentContext: ComponentContext) =
        AuthComponent.Child.EmailVerification(
            RealEmailVerificationComponent(
                componentContext = componentContext,
                onSendCodeClick = { email -> navigation.push(Config.CodeConfirmation(email)) },
                onBackClick = ::onBackClick,
            )
        )

    private fun createCodeConfirmationComponent(
        componentContext: ComponentContext,
        config: Config.CodeConfirmation
    ) =
        AuthComponent.Child.CodeConfirmation(
            RealCodeConfirmationComponent(
                componentContext = componentContext,
                email = config.email,
                onCodeConfirm = { TODO("Navigation to Enter fields screen") },
                onBackClick = ::onBackClick,
            )
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config

        @Serializable
        data object EmailVerification : Config

        @Serializable
        data class CodeConfirmation(val email: String) : Config
    }
}
