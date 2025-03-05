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
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.modifiers.isPhone
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.CodeConfirmationScreen
import kaiyrzhan.de.empath.features.auth.ui.components.LowPolyBackground
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.EmailVerificationScreen
import kaiyrzhan.de.empath.features.auth.ui.login.LoginScreen


@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun AuthScreen(
    component: AuthComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()

    if (currentWindowInfo.isPhone()) {
        Children(
            stack = component.stack,
            modifier = modifier.fillMaxSize(),
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = { component.onBackClick() },
                fallbackAnimation = stackAnimation(fade()),
            ),
        ) { child ->
            when (val instance = child.instance) {
                is AuthComponent.Child.Login -> LoginScreen(instance.component)
                is AuthComponent.Child.EmailVerification -> EmailVerificationScreen(instance.component)
                is AuthComponent.Child.CodeConfirmation -> CodeConfirmationScreen(instance.component)
//                    is AuthComponent.Child.PasswordRecovery -> PasswordRecoveryContent(instance.component)
//                    is AuthComponent.Child.OptionalUserInfo -> OptionalUserInfoContent(instance.component)
//                    is AuthComponent.Child.CreateAccount -> CreateAccountContent(instance.component)
//                    is AuthComponent.Child.Privacy -> PrivacyContent(instance.component)
            }
        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LowPolyBackground(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp, horizontal = 12.dp),
            )
            Children(
                stack = component.stack,
                modifier = modifier.weight(1f),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = { component.onBackClick() },
                    fallbackAnimation = stackAnimation(fade()),
                ),
            ) { child ->
                when (val instance = child.instance) {
                    is AuthComponent.Child.Login -> LoginScreen(instance.component)
                    is AuthComponent.Child.EmailVerification -> EmailVerificationScreen(instance.component)
                    is AuthComponent.Child.CodeConfirmation -> CodeConfirmationScreen(instance.component)
//                    is AuthComponent.Child.PasswordRecovery -> PasswordRecoveryContent(instance.component)
//                    is AuthComponent.Child.OptionalUserInfo -> OptionalUserInfoContent(instance.component)
//                    is AuthComponent.Child.CreateAccount -> CreateAccountContent(instance.component)
//                    is AuthComponent.Child.Privacy -> PrivacyContent(instance.component)
                }
            }
        }
    }
}
