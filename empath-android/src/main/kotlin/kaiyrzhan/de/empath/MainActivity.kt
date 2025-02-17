package kaiyrzhan.de.empath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kaiyrzhan.de.empath.compose.EmpathApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmpathApp()
        }
    }
}
