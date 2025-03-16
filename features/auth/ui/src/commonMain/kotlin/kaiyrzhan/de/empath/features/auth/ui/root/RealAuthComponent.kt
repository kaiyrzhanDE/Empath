package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.RealCodeConfirmationComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.RealEmailVerificationComponent
import kaiyrzhan.de.empath.features.auth.ui.login.RealLoginComponent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.RealPasswordRecoveryComponent
import kaiyrzhan.de.empath.features.auth.ui.root.model.VerificationType
import kaiyrzhan.de.empath.features.auth.ui.signUp.RealSignUpComponent
import kotlinx.serialization.Serializable

public class RealAuthComponent(
    componentContext: ComponentContext,
    private val onLoginClick: () -> Unit,
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
        is Config.EmailVerification -> createEmailVerificationComponent(
            childComponentContext,
            config
        )

        is Config.CodeConfirmation -> createCodeConfirmationComponent(childComponentContext, config)
        is Config.SignUp -> createSignUpComponent(childComponentContext, config)
        is Config.PasswordRecovery -> createPasswordRecoveryComponent(childComponentContext, config)
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createLoginComponent(componentContext: ComponentContext) =
        AuthComponent.Child.Login(
            RealLoginComponent(
                componentContext = componentContext,
                onLoginClick = onLoginClick,
                onSignUpClick = {
                    navigation.push(Config.EmailVerification(type = VerificationType.SIGN_UP))
                },
                onPasswordResetClick = {
                    navigation.push(Config.EmailVerification(type = VerificationType.RESET_PASSWORD))
                }
            ),
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createEmailVerificationComponent(
        componentContext: ComponentContext,
        config: Config.EmailVerification
    ) =
        AuthComponent.Child.EmailVerification(
            RealEmailVerificationComponent(
                componentContext = componentContext,
                email = config.email,
                verificationType = config.type,
                onSendResetPasswordCodeClick = { email ->
                    navigation.push(Config.CodeConfirmation(email, config.type))
                },
                onSendSignUpCodeClick = { email ->
                    navigation.push(Config.CodeConfirmation(email, config.type))
                },
                onSignUpClick = { email ->
                    navigation.replaceCurrent(
                        Config.EmailVerification(
                            email = email,
                            type = VerificationType.SIGN_UP,
                        )
                    )
                },
                onResetPasswordClick = { email ->
                    navigation.replaceCurrent(
                        Config.EmailVerification(
                            email = email,
                            type = VerificationType.RESET_PASSWORD,
                        )
                    )
                },
                onBackClick = ::onBackClick,
            )
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createCodeConfirmationComponent(
        componentContext: ComponentContext,
        config: Config.CodeConfirmation,
    ) = AuthComponent.Child.CodeConfirmation(
        RealCodeConfirmationComponent(
            componentContext = componentContext,
            email = config.email,
            verificationType = config.type,
            onCodeConfirm = { email -> navigation.push(Config.SignUp(email)) },
            onBackClick = ::onBackClick,
        )
    )

    private fun createSignUpComponent(
        componentContext: ComponentContext,
        config: Config.SignUp,
    ) = AuthComponent.Child.SignUp(
        RealSignUpComponent(
            componentContext = componentContext,
            email = config.email,
            onSignUpClick = { TODO("Navigation to Main screen") },
            onBackClick = ::onBackClick,
        )
    )

    private fun createPasswordRecoveryComponent(
        componentContext: ComponentContext,
        config: Config.PasswordRecovery,
    ) = AuthComponent.Child.PasswordRecovery(
        RealPasswordRecoveryComponent(
            componentContext = componentContext,
            email = config.email,
            onPasswordReset = { TODO("Navigation to login screen again") },
            onBackClick = ::onBackClick,
        )
    )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config

        @Serializable
        data class EmailVerification(val email: String = "", val type: VerificationType) : Config

        @Serializable
        data class CodeConfirmation(val email: String, val type: VerificationType) : Config

        @Serializable
        data class SignUp(val email: String) : Config

        @Serializable
        data class PasswordRecovery(val email: String) : Config
    }
}
