package kaiyrzhan.de.empath.features.auth.ui.root.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun LowPolyBackground(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.large,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.background_low_poly),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomEnd,
            contentDescription = "Low poly blue background",
        )
    }
}