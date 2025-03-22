package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_error
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun StudyProperties(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
    ) {
        Header(text = "Study")
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Analytics",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Task manager",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Recommendations",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Forum",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Anki cards",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
    }
}