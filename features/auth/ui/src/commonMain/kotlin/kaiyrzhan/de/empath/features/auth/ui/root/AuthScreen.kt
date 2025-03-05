package kaiyrzhan.de.empath.features.auth.ui.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.CodeConfirmationScreen
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.EmailVerificationScreen
import kaiyrzhan.de.empath.features.auth.ui.login.LoginScreen


@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun AuthScreen(
    component: AuthComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier.fillMaxSize(),
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            onBack = { component.onBackClick() },
            fallbackAnimation = stackAnimation(slide() + fade()),
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
