package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
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
) : BaseComponent(componentContext), AuthComponent {

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
        componentContext: ComponentContext,
    ): AuthComponent.Child {
        logger.d(this.className(), "Auth child: $config")
        return when (config) {
            is Config.Login ->
                createLoginComponent(componentContext)

            is Config.EmailVerification ->
                createEmailVerificationComponent(componentContext, config)

            is Config.CodeConfirmation ->
                createCodeConfirmationComponent(componentContext, config)

            is Config.SignUp ->
                createSignUpComponent(componentContext, config)

            is Config.PasswordRecovery ->
                createPasswordRecoveryComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createLoginComponent(componentContext: ComponentContext) =
        AuthComponent.Child.Login(
            RealLoginComponent(
                componentContext = componentContext,
                onLoginClick = onLoginClick,
                onSignUpClick = {
                    navigation.push(
                        Config.EmailVerification(VerificationType.SIGN_UP)
                    )
                },
                onPasswordResetClick = {
                    navigation.push(
                        Config.EmailVerification(VerificationType.RESET_PASSWORD)
                    )
                },
            ),
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createEmailVerificationComponent(
        componentContext: ComponentContext,
        config: Config.EmailVerification
    ) = AuthComponent.Child.EmailVerification(
        RealEmailVerificationComponent(
            componentContext = componentContext,
            email = config.email,
            verificationType = config.type,
            onBackClick = ::onBackClick,
            onSendResetPasswordCodeClick = { email ->
                navigation.push(Config.CodeConfirmation(email, VerificationType.RESET_PASSWORD))
            },
            onSendSignUpCodeClick = { email ->
                navigation.push(Config.CodeConfirmation(email, VerificationType.SIGN_UP))
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
            onBackClick = ::onBackClick,
            onSignUpCodeConfirm = { email -> navigation.push(Config.SignUp(email)) },
            onResetPasswordCodeConfirm = { email -> navigation.push(Config.PasswordRecovery(email)) },
        )
    )

    private fun createSignUpComponent(
        componentContext: ComponentContext,
        config: Config.SignUp,
    ) = AuthComponent.Child.SignUp(
        RealSignUpComponent(
            componentContext = componentContext,
            email = config.email,
            onBackClick = {
                navigation.popWhile { config -> config !is Config.EmailVerification }
            },
            onSignUpClick = {
                navigation.popWhile { config -> config !is Config.Login }
            },
        )
    )

    private fun createPasswordRecoveryComponent(
        componentContext: ComponentContext,
        config: Config.PasswordRecovery,
    ) = AuthComponent.Child.PasswordRecovery(
        RealPasswordRecoveryComponent(
            componentContext = componentContext,
            email = config.email,
            onBackClick = {
                navigation.popWhile { config -> config !is Config.EmailVerification }
            },
            onPasswordReset = {
                navigation.popWhile { config -> config !is Config.Login }
            },
        )
    )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config

        @Serializable
        data class EmailVerification(val email: String, val type: VerificationType) : Config {
            constructor(type: VerificationType) : this(email = "", type = type)
        }

        @Serializable
        data class CodeConfirmation(val email: String, val type: VerificationType) : Config

        @Serializable
        data class SignUp(val email: String) : Config

        @Serializable
        data class PasswordRecovery(val email: String) : Config
    }
}
