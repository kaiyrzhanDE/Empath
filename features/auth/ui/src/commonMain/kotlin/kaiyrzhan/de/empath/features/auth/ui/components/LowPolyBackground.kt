package kaiyrzhan.de.empath.features.auth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import empath.features.auth.ui.generated.resources.background_low_poly
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import empath.features.auth.ui.generated.resources.Res as FeatureRes

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
            painter = painterResource(FeatureRes.drawable.background_low_poly),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomEnd,
            contentDescription = "Low poly blue background",
        )
    }
}