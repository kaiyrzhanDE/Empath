package kaiyrzhan.de.empath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import kaiyrzhan.de.empath.compose.EmpathApp
import kaiyrzhan.de.empath.root.RealRootComponent

class MainActivity : ComponentActivity() {
    private val roomComponent by lazy {
        RealRootComponent(componentContext = defaultComponentContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmpathApp(
                component = roomComponent,
            )
        }
    }
}
