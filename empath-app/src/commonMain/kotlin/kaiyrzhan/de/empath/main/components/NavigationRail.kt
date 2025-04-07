package kaiyrzhan.de.empath.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.main.MainRootComponent
import kaiyrzhan.de.empath.main.MainRootComponent.Child
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NavigationRail(
    component: MainRootComponent,
    currentChild: Child,
    modifier: Modifier = Modifier,
) {
    val colors = NavigationRailItemDefaults.colors(
        selectedIconColor = EmpathTheme.colors.onSurface,
        selectedTextColor = EmpathTheme.colors.onSurface,
        indicatorColor = EmpathTheme.colors.primary,
        unselectedIconColor = EmpathTheme.colors.onSurfaceVariant,
        unselectedTextColor = EmpathTheme.colors.onSurfaceVariant,
    )
    Row(
        modifier = modifier,
    ) {
        NavigationRail(
            modifier = Modifier.wrapContentWidth(),
            containerColor = EmpathTheme.colors.scrim,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            NavigationRailItem(
                selected = currentChild is Child.Vacancies,
                onClick = component::onVacanciesTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Vacancies) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Vacancies",
                    )
                },
                label = {
                    Text(
                        text = "Vacancies",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
            Spacer(modifier = Modifier.height(12.dp))
            NavigationRailItem(
                selected = currentChild is Child.Analytics,
                onClick = component::onAnalyticsTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Analytics) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Analytics",
                    )
                },
                label = {
                    Text(
                        text = "Analytics",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
            Spacer(modifier = Modifier.height(12.dp))
            NavigationRailItem(
                selected = currentChild is Child.Menu,
                onClick = component::onMenuTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Menu) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Menu",
                    )
                },
                label = {
                    Text(
                        text = "Menu",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
            Spacer(modifier = Modifier.height(12.dp))
            NavigationRailItem(
                selected = currentChild is Child.Articles,
                onClick = component::onArticlesTabClick,
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = if (currentChild is Child.Articles) Res.drawable.ic_local_library_filled
                            else Res.drawable.ic_local_library_outlined
                        ),
                        contentDescription = stringResource(Res.string.articles),
                    )
                },
                label = {
                    Text(
                        text = stringResource(Res.string.articles),
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
            Spacer(modifier = Modifier.height(12.dp))
            NavigationRailItem(
                selected = currentChild is Child.Profile,
                onClick = component::onProfileTabClick,
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = if (currentChild is Child.Profile) Res.drawable.ic_person_filled
                            else Res.drawable.ic_person_outlined,
                        ),
                        contentDescription = stringResource(Res.string.profile),
                    )
                },
                label = {
                    Text(
                        text = stringResource(Res.string.profile),
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        VerticalDivider(
            color = EmpathTheme.colors.outlineVariant,
        )
    }
}