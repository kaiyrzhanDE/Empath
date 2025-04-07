package kaiyrzhan.de.empath.features.auth.ui.root

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.ui.animations.swipe
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.CodeConfirmationScreen
import kaiyrzhan.de.empath.features.auth.ui.root.components.LowPolyBackground
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.EmailVerificationScreen
import kaiyrzhan.de.empath.features.auth.ui.login.LoginScreen
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.PasswordRecoveryScreen
import kaiyrzhan.de.empath.features.auth.ui.signUp.SignUpScreen


@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun AuthRootScreen(
    component: AuthRootComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()

    if (currentWindowInfo.isPhone()) {
        AuthRootScreen(
            component = component,
            modifier = modifier.fillMaxSize(),
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LowPolyBackground(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp, horizontal = 12.dp),
            )
            AuthRootScreen(
                component = component,
                modifier = modifier.weight(1f),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = component::onBackClick,
                    fallbackAnimation = stackAnimation(fade()),
                ),
            )
        }
    }
}

@Composable
private fun AuthRootScreen(
    component: AuthRootComponent,
    modifier: Modifier = Modifier,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is AuthRootComponent.Child.Login -> {
                LoginScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is AuthRootComponent.Child.EmailVerification -> {
                EmailVerificationScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is AuthRootComponent.Child.CodeConfirmation -> {
                CodeConfirmationScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is AuthRootComponent.Child.SignUp -> {
                SignUpScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is AuthRootComponent.Child.PasswordRecovery -> {
                PasswordRecoveryScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
