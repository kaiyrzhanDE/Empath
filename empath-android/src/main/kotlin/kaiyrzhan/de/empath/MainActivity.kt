package kaiyrzhan.de.empath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import kaiyrzhan.de.empath.root.RootScreen
import kaiyrzhan.de.empath.root.RealRootComponent

class MainActivity : ComponentActivity() {
    private val roomComponent by lazy {
        retainedComponent{ componentContext -> RealRootComponent(componentContext) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen(
                component = roomComponent,
            )
        }
    }
}
