@file:JvmName("Empath")

package kaiyrzhan.de.empath

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import io.github.vinceglb.filekit.FileKit
import kaiyrzhan.de.empath.di.initKoin
import kaiyrzhan.de.empath.root.RootScreen
import kaiyrzhan.de.empath.utils.readSerializableContainer
import kaiyrzhan.de.empath.root.RealRootComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.startKoin
import java.io.File

private const val SAVED_STATE_FILE_NAME = "saved_state.dat"

public fun main() {
    startKoin {
        initKoin(
            isDebug = false,
        )
    }
    FileKit.init(appId = "Empath")

    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher(File(SAVED_STATE_FILE_NAME).readSerializableContainer())

    val rootComponent = runOnUiThread {
        RealRootComponent(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
            ),
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = stringResource(Res.string.app_name),
            icon = painterResource(Res.drawable.ic_app),
        ) {
            RootScreen(
                component = rootComponent,
            )
        }
    }
}
