package kaiyrzhan.de.empath.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.empty_title
import empath.core.uikit.generated.resources.under_development
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
public fun UnderDevelopmentScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.empty_title),
    description: String = stringResource(Res.string.under_development),
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/under_development.json").decodeToString()
        )
    }
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .heightIn(max = 300.dp)
                .aspectRatio(1f)
                .fillMaxWidth(),
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.defaultMaxWidth(),
            text = title,
            style = EmpathTheme.typography.titleLarge,
            color = EmpathTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.defaultMaxWidth(),
            text = description,
            style = EmpathTheme.typography.labelLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}
