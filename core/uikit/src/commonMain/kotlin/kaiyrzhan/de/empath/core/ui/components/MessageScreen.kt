package kaiyrzhan.de.empath.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.empty_description
import empath.core.uikit.generated.resources.empty_title
import empath.core.uikit.generated.resources.ic_broken_ice
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
public fun MessageScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.empty_title),
    description: String = stringResource(Res.string.empty_description),
) {
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(Res.drawable.ic_broken_ice),
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