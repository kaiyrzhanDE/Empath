package kaiyrzhan.de.empath.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.root.AuthScreen
import kaiyrzhan.de.empath.root.RootComponent

@OptIn(ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class)
@Composable
public fun EmpathApp(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    EmpathTheme {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            color = EmpathTheme.colors.surface,
        ) {
            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize(),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = { component.onBackClicked() },
                    fallbackAnimation = stackAnimation(scale()),
                ),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Auth -> AuthScreen(instance.component)
                }
            }
        }
    }
}

