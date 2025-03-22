package kaiyrzhan.de.empath.features.profile.ui.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
public fun ProfileRootScreen(
    component: RootProfileComponent,
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(EmpathTheme.colors.scrim),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}