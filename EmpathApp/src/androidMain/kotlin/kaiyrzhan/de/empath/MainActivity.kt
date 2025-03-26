package kaiyrzhan.de.empath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init
import kaiyrzhan.de.empath.root.RootScreen
import kaiyrzhan.de.empath.root.RealRootComponent

public class MainActivity : ComponentActivity() {
    private val roomComponent by lazy {
        retainedComponent{ componentContext -> RealRootComponent(componentContext) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileKit.init(this)
        setContent {
            RootScreen(
                component = roomComponent,
            )
        }
    }
}
