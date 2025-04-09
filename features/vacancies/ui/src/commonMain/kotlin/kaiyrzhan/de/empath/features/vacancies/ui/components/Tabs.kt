package kaiyrzhan.de.empath.features.vacancies.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.model.Tab
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Tabs(
    modifier: Modifier = Modifier,
    currentTab: Tab,
    tabs: List<Tab>,
    selectedTabIndex: Int,
    onClick: (index: Int) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = tabs.indexOf(currentTab),
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
            divider = {},
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onClick(index)},
                    selectedContentColor = EmpathTheme.colors.primary,
                    unselectedContentColor = EmpathTheme.colors.onSurfaceVariant,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 14.dp),
                        text = stringResource(tab.res),
                        style = EmpathTheme.typography.titleSmall,
                    )
                }

            }
        }
    }
}