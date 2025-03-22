package kaiyrzhan.de.empath.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
private fun animateTextAsState(
    text: String,
    intervalMillis: Long = 1000L,
): State<String> {
    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(intervalMillis)
            dotCount = (dotCount + 1) % 4 // 0, 1, 2, 3
        }
    }

    val animatedText = remember(text, dotCount) {
        derivedStateOf { text + ".".repeat(dotCount) }
    }

    return animatedText
}

@Composable
public fun CircularLoadingScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.loading),
    indicatorSize: Dp = 48.dp,
) {
    val text = animateTextAsState(title)

    Column(
        modifier = modifier
            .background(EmpathTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            trackColor = EmpathTheme.colors.secondary,
            strokeCap = StrokeCap.Square,
            color = EmpathTheme.colors.primary,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = text.value,
            style = EmpathTheme.typography.titleLarge,
            color = EmpathTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(Res.string.loading_prompt),
            style = EmpathTheme.typography.labelLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}

@Composable
public fun CircularLoadingCard(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.loading),
    indicatorSize: Dp = 48.dp,
) {
    val text = animateTextAsState(title)

    Row(
        modifier = modifier
            .background(EmpathTheme.colors.surface),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            trackColor = EmpathTheme.colors.secondary,
            strokeCap = StrokeCap.Square,
            color = EmpathTheme.colors.primary,
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = text.value,
                style = EmpathTheme.typography.titleLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = stringResource(Res.string.loading_prompt),
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
    }
}