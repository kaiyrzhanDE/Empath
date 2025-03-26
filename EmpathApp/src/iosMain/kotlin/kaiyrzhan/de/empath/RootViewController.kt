package kaiyrzhan.de.empath

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureIcon
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import kaiyrzhan.de.empath.root.RootScreen
import kaiyrzhan.de.empath.di.initKoin
import kaiyrzhan.de.empath.root.RootComponent
import org.koin.core.context.startKoin
import platform.Foundation.NSProcessInfo
import platform.UIKit.UIViewController

@OptIn(ExperimentalDecomposeApi::class)
public fun rootViewController(
    root: RootComponent,
    backDispatcher: BackDispatcher,
): UIViewController {
    return ComposeUIViewController(
        content = {
            PredictiveBackGestureOverlay(
                backDispatcher = backDispatcher,
                backIcon = { progress, _ ->
                    PredictiveBackGestureIcon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        progress = progress,
                    )
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                RootScreen(component = root, modifier = Modifier.fillMaxSize())
            }
        },
    )
}
